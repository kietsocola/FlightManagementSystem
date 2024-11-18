package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachCreateDTO;
import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachUpdateDTO;
import com.project.flightManagement.Mapper.HanhKhachMapper;
import com.project.flightManagement.Model.HanhKhach;
import com.project.flightManagement.Repository.HanhKhachRepository;
import com.project.flightManagement.Repository.VeRepository;
import com.project.flightManagement.Service.HanhKhachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HanhKhachServiceImpl implements HanhKhachService {
    @Autowired
    private HanhKhachMapper hanhKhachMapper;
    @Autowired
    private HanhKhachRepository hanhKhachRepository;
    @Autowired
    private VeRepository veRepository;
    @Override
    public boolean existHanhKhachByIdHanhKhach(int idHanhKhach) {
        return hanhKhachRepository.existsById(idHanhKhach);
    }



    @Override
    public HanhKhach createHanhKhach(HanhKhachCreateDTO hanhKhachCreateDTO) {
        HanhKhach hanhKhach = hanhKhachMapper.toHanhKhach(hanhKhachCreateDTO);
        return hanhKhachRepository.save(hanhKhach);
    }

    @Override
    public Optional<HanhKhach> getHanhKhachById(int idHanhKhach) {
        return hanhKhachRepository.findById(idHanhKhach);
    }

    @Override
    public HanhKhach updateHanhKhach(HanhKhachUpdateDTO hanhKhachUpdateDTO) {
        HanhKhach hanhKhach = HanhKhachMapper.toHanhKhachFromHanhKhachUpdateDTO(hanhKhachUpdateDTO);
        return hanhKhachRepository.save(hanhKhach);
    }

    @Override
    public HanhKhach saveNewHanhKhachWhenBooking(HanhKhach hk) {
        return hanhKhachRepository.save(hk);
    }


    public List<Map<String, Object>> getPassengerStatistics(LocalDateTime startDate, LocalDateTime endDate, String groupByType) {
        List<Object[]> results = hanhKhachRepository.findPassengerCountByGroup(startDate, endDate, groupByType);

        List<Map<String, Object>> formattedResults = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("timeGroup", row[1]); // Tháng, quý hoặc năm
            data.put("ageGroup", row[0]); // Nhóm tuổi
            data.put("passengerCount", row[2]); // Số lượng hành khách
            formattedResults.add(data);
        }

        return formattedResults;
    }

    @Override
    public Optional<HanhKhach> findByCccd(String cccd) {
        return hanhKhachRepository.findByCccd(cccd);
    }
}

