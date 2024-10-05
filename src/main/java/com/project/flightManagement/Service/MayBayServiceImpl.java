package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.MayBayDTO.MayBayDTO;
import com.project.flightManagement.Mapper.MayBayMapper;
import com.project.flightManagement.Model.KhachHang;
import com.project.flightManagement.Model.MayBay;
import com.project.flightManagement.Repository.MayBayRepository;
import com.project.flightManagement.Service.MayBayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MayBayServiceImpl implements MayBayService {
    @Autowired
    private MayBayRepository mbRepo;

    @Override
    public Iterable<MayBayDTO> getAllMayBay() {
        try {
            Iterable<MayBay> listMB = mbRepo.findAll();
            Iterable<MayBayDTO> listMbDTO = StreamSupport.stream(listMB.spliterator(), false).map(MayBayMapper::toDTO)
                    .toList();
            return listMbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while fetching planes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<MayBayDTO> getMayBayById(int id) {
        try {
            Optional<MayBay> mb = mbRepo.findById(id);
            Optional<MayBayDTO> mbDTO = mb.map(MayBayMapper::toDTO);
            return mbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get planes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<MayBayDTO> getMayBayByIcao(int icao) {
        try {
            Optional<MayBay> mb = mbRepo.findByIcao(icao);
            Optional<MayBayDTO> mbDTO = mb.map(MayBayMapper::toDTO);
            return mbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get planes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<MayBayDTO> getMayBayBySoHieu(int soHieu) {
        try {
            Optional<MayBay> mb = mbRepo.findBySoHieu(soHieu);
            Optional<MayBayDTO> mbDTO = mb.map(MayBayMapper::toDTO);
            return mbDTO;
        } catch (Exception e) {
            System.err.println("Error occurred while get planes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<MayBayDTO> getMayBayBySoLuongGhe(int soLuongGhe) {
        List<MayBay> maybayList = mbRepo.findBySoLuongGhe(soLuongGhe);
        return maybayList.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MayBayDTO> getMayBayByNamSanXuat(int namSanXuat) {
        List<MayBay> maybayList = mbRepo.findByNamSanXuat(namSanXuat);
        return maybayList.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<MayBayDTO> addNewMayBay(MayBayDTO mayBayDTO) {
        try {
            MayBay mb = MayBayMapper.toEntity(mayBayDTO);
            MayBay savedMB = mbRepo.save(mb);
            return Optional.of(MayBayMapper.toDTO(savedMB));
        } catch (Exception e) {
            System.err.println("Error occurred while get planes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<MayBayDTO> upDateMayBay(MayBayDTO mayBayDTO) {
        Optional<MayBay> existingKH = mbRepo.findById(mayBayDTO.getIdMayBay());
        if (existingKH.isPresent()) {
            MayBay maybay = MayBayMapper.toEntity(mayBayDTO);
            MayBay updatedMayBay = mbRepo.save(maybay);
            return Optional.of(MayBayMapper.toDTO(updatedMayBay));
        } else {
            System.err.println("Plane does not existing!!!");
            return Optional.empty();
        }
    }

    @Override
    public Iterable<MayBayDTO> getAllMayBaySorted(String sortBy) {
        try {
            List<MayBay> mayBayList = mbRepo.findAll(Sort.by(Sort.Direction.DESC, sortBy));
            List<MayBayDTO> mayBayDTOList = mayBayList.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
            return mayBayDTOList;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid sorting filed: " + sortBy);
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("An error occurred while fetching sorted planes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<MayBayDTO> findAllMayBayByIdHangBay(int idHangBay) {
        List<MayBay> maybayList = mbRepo.findByIdHangBay(idHangBay);
        return maybayList.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MayBayDTO> findAllMayBayByTen(String keyword) {
        List<MayBay> mayBayList = mbRepo.findByName(keyword);
        return mayBayList.stream().map(MayBayMapper::toDTO).collect(Collectors.toList());
    }
}