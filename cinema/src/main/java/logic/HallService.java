package logic;

import model.Seat;
import model.User;
import persistent.DBStore;
import persistent.Store;
import java.util.List;

public class HallService implements Service {
    private final static HallService INSTANCE = new HallService();
    private final Store store = DBStore.getInstance();

    public HallService() {
    }

    public static HallService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Seat> seats() {
        return store.seats();
    }

    @Override
    public void executePay(Seat seat, User user) {
        if (validateSeat(seat)) {
            store.executePay(seat, user);
        } else {
            throw new IllegalStateException("Wrong price or seat is sold");
        }
    }

    private boolean validateSeat(Seat seat) {
        Seat seatDB = store.seat(seat);
        return !(seatDB.getPrice() != seat.getPrice() || seatDB.isSold());
    }
}
