package com.project.flightManagement.Service;

import com.project.flightManagement.Model.Ve;
import org.springframework.stereotype.Service;

@Service
public interface HoldSeatService {
    public void holdSeat(int idVe);
    public void cancelSeat(int idVe);
    public void confirmBooking(int idVe);
}
