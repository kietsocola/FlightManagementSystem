package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.TaiKhoanDTO.TaiKhoanDTO;
import com.project.flightManagement.Mapper.TaiKhoanMapper;
import com.project.flightManagement.Model.TaiKhoan;
import com.project.flightManagement.Repository.TaiKhoanRepository;
import com.project.flightManagement.Service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepository tkRepo;

    @Override
    public Iterable<TaiKhoanDTO> getAllTaiKhoan() {
        try {
            Iterable<TaiKhoan> listKH = tkRepo.findAll();
            Iterable<TaiKhoanDTO> listTkDTO = StreamSupport.stream(listKH.spliterator(), false)
                    .map(TaiKhoanMapper::toDTO)
                    .toList();
            return listTkDTO;
        } catch (Exception e){
            System.err.println("Error occurred while fetching accounts: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<TaiKhoanDTO> getTaiKhoanByID(int idTaiKhoan) {
        try {
            Optional<TaiKhoan> tk = tkRepo.findById(idTaiKhoan);
            System.out.println("tai khoan: " + tk);
            Optional<TaiKhoanDTO> tkDTO = tk.map(TaiKhoanMapper::toDTO);
            System.out.println("tk dto : " + tkDTO);
            return tkDTO;
        }catch (Exception e){
            System.err.println("Error occurred while get account: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<TaiKhoanDTO> getTaiKhoanByTenDN(String tenDN) {
        try {
            Optional<TaiKhoan> tk = tkRepo.findByTenDangNhap(tenDN);
            Optional<TaiKhoanDTO> tkDTO = tk.map(TaiKhoanMapper::toDTO);
            return tkDTO;
        } catch (Exception e) {
            System.err.println("Error occurred  while get account: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<TaiKhoanDTO> addTaiKhoan(TaiKhoanDTO tkDTO) {
        try {
            TaiKhoan tk = TaiKhoanMapper.toEntity(tkDTO);
            TaiKhoan savedTK = tkRepo.save(tk);
            System.out.println(savedTK);
            return Optional.of(TaiKhoanMapper.toDTO(savedTK));
        }catch (Exception e){
            System.err.println("Error occurred while save account: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<TaiKhoanDTO> updateTaiKhoan(TaiKhoanDTO khDTO) {
        Optional<TaiKhoan> existingTK = tkRepo.findById((khDTO.getIdTaiKhoan()));
        if(existingTK.isPresent()){
            TaiKhoan TaiKhoan = TaiKhoanMapper.toEntity(khDTO);
            TaiKhoan updatedTaiKhoan = tkRepo.save(TaiKhoan);
            return Optional.of(TaiKhoanMapper.toDTO(updatedTaiKhoan));
        }else {
            System.err.println("Customer does not existing!!!");
            return Optional.empty();
        }
    }
}
