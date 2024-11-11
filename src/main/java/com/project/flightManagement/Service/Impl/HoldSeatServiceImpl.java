package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.Enum.VeEnum;
import com.project.flightManagement.Mapper.HanhKhachMapper;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Repository.HanhKhachRepository;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.HoldSeatService;
import com.project.flightManagement.Service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class HoldSeatServiceImpl implements HoldSeatService {
    @Autowired
    private VeRepository veRepo;
    @Autowired
    private HanhKhachRepository hanhKhachRepo;
    @Autowired
    private SocketIOService socketIOService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private Map<Integer, ScheduledFuture<?>> holdSeatTasks = new HashMap<>();
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    public HoldSeatServiceImpl() {
        taskScheduler.setPoolSize(10);  // Định nghĩa số lượng luồng
        taskScheduler.initialize();
    }
    @Override
    public void holdSeat(int idVe) {
        Ve ve = veRepo.findById(idVe).get();
// Cập nhật trạng thái vé thành "HOLD"
        ve.setTrangThai(VeEnum.HOLD);
        veRepo.save(ve);

        // Tạo tác vụ để hủy ghế sau 5 phút
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(() -> cancelSeat(idVe),
                new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10)));

        // Lưu tác vụ vào bản đồ để quản lý
        holdSeatTasks.put(ve.getIdVe(), scheduledTask);
    }

    @Override
    public void cancelSeat(int idVe) {
        Ve ve = veRepo.findById(idVe).get();
        if (ve.getTrangThai() == VeEnum.HOLD) {
            ve.setTrangThai(VeEnum.EMPTY);  // Trả vé lại trạng thái AVAILABLE
            veRepo.save(ve);
            System.out.println("Vé " + ve.getIdVe() + " đã được hủy.");
            String message = "Seat "+ve.getChoNgoi().getIdChoNgoi()+" is now available";

            // Gửi thông điệp qua Socket.IO
            messagingTemplate.convertAndSend("/topic/seatCancelHold", ve.getChoNgoi().getIdChoNgoi());
        }
    }

    @Override
    public void confirmBooking(HanhKhachDTO hk) { //ở đây có đủ id Vé và id Khách hàng nên ai làm hóa đơn thì ok
        Ve ve = veRepo.findById(hk.getIdVe()).get();
        if (holdSeatTasks.containsKey(ve.getIdVe())) {
            holdSeatTasks.get(ve.getIdVe()).cancel(false);  // Hủy tác vụ hủy ghế
            holdSeatTasks.remove(ve.getIdVe());  // Xóa khỏi bản đồ
        }
        try{
            HanhKhach hkEntity = HanhKhachMapper.toEntity(hk);
            hkEntity = hanhKhachRepo.save(hkEntity);
            ve.setHanhKhach(hkEntity);
            ve.setTrangThai(VeEnum.BOOKED);  // Cập nhật trạng thái thành BOOKED
            veRepo.save(ve);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
