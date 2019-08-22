package persistent;

import model.Seat;
import model.User;
import java.util.List;

/**
 * Store interface
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.08.2019
 */
public interface Store {
    List<Seat> seats();
    Seat seat(Seat seat);
    boolean executePay(Seat seat, User user);
}
