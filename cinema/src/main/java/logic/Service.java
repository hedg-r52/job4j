package logic;

import model.Seat;
import model.User;
import java.util.List;

public interface Service {
    List<Seat> seats();
    void executePay(Seat seat, User user);
}
