package mb.DataStructures;

import java.util.Arrays;

public class Root {


    private Dealer[] dealers;
    private Booking[] bookings;


    public Dealer[] getDealers() {
        return dealers;
    }

    public void setDealers(Dealer[] dealers) {
        this.dealers = dealers;
    }

    public Booking[] getBookings() {
        return bookings;
    }

    public void setBookings(Booking[] bookings) {
        this.bookings = bookings;
    }

    public void addBookings(Booking booking) {
        final int N = bookings.length;
        bookings = Arrays.copyOf(bookings, N + 1);
        bookings[N] = booking;
    }
}
