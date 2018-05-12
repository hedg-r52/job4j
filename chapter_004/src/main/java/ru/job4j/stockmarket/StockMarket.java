package ru.job4j.stockmarket;

import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Биржа
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StockMarket {
    private HashMap<String, TreeSet<Record>> recs;

    public StockMarket() {
        this.recs = new HashMap<>();
    }

    public void exec(Request req) {
        TreeSet<Record> issuerRecs = getIssuerRecs(req);
        if (req.getType() == RequestType.ADD) {
            if (req.getAction() == RequestAction.ASK) {
                addAskRequest(issuerRecs, req);
            } else {
                addBidRequest(issuerRecs, req);
            }
        } else {
            if (req.getAction() == RequestAction.ASK) {
                deleteAskRequest(issuerRecs, req);
            } else {
                deleteBidRequest(issuerRecs, req);
            }
        }
        this.recs.put(req.getBook(), issuerRecs);
    }

    public String getIssuerInfo(String issuer) {
        StringBuilder result = new StringBuilder();
        TreeSet<Record> issuerRecs = recs.get(issuer);
        final String formatString = "%20s | %20s | %20s\r\n";
        if (issuerRecs.size() > 0) {
            result.append(String.format(formatString, "buy", "price", "sell"));
            for (Record rec : recs.get(issuer)) {
                result.append(String.format(formatString, rec.buy, rec.price, rec.sell));
            }
        }
        return result.toString();
    }


    private void addAskRequest(TreeSet<Record> issuerRecs, Request req) {
        int count = req.getVolume();
        for (Record rec : issuerRecs) {
            if (rec.price > req.getPrice()) {
                break;
            }
            if (rec.sell == 0) {
                continue;
            }
            if (rec.sell < count) {
                count -= rec.sell;
                rec.sell = 0;
            } else {
                rec.sell -= count;
                count = 0;
            }
            if (count == 0) {
                break;
            }
        }
        issuerRecs.removeIf((Record p) -> (p.sell == 0 && p.buy == 0));
        if (count > 0) {
            Record findedRec = getRecByPrice(issuerRecs, req.getPrice());
            if (findedRec == null) {
                issuerRecs.add(new Record(0, req.getPrice(), count));
            } else {
                findedRec.buy += count;
            }
        }

    }

    private void addBidRequest(TreeSet<Record> issuerRecs, Request req) {
        int count = req.getVolume();
        for (Record rec : issuerRecs.descendingSet()) {
            if (rec.price < req.getPrice()) {
                break;
            }
            if (rec.buy == 0) {
                continue;
            }
            if (rec.buy < count) {
                count -= rec.buy;
                rec.buy = 0;
            } else {
                rec.buy -= count;
                count = 0;
            }
            if (count == 0) {
                break;
            }
        }
        issuerRecs.removeIf((Record p) -> (p.sell == 0 && p.buy == 0));
        if (count > 0) {
            Record findedRec = getRecByPrice(issuerRecs, req.getPrice());
            if (findedRec == null) {
                issuerRecs.add(new Record(count, req.getPrice(), 0));
            } else {
                findedRec.sell += count;
            }
        }
    }

    private boolean deleteAskRequest(TreeSet<Record> issuerRecs, Request req) {
        boolean result = false;
        Record rec = getRecByPrice(issuerRecs, req.getPrice());
        if (rec != null) {
            rec.buy = (rec.buy - req.getVolume() > 0 ? rec.buy - req.getVolume() : 0);
        }
        return result;
    }

    private boolean deleteBidRequest(TreeSet<Record> issuerRecs, Request req) {
        boolean result = false;
        Record rec = getRecByPrice(issuerRecs, req.getPrice());
        if (rec != null) {
            rec.sell = (rec.sell - req.getVolume() > 0 ? rec.sell - req.getVolume() : 0);
        }
        return result;
    }

    private Record getRecByPrice(TreeSet<Record> issuerRecs, double price) {
        Record searchingElem = new Record(0, price, 0);
        return issuerRecs.subSet(searchingElem, true, searchingElem, true).floor(searchingElem);
    }

    private TreeSet<Record> getIssuerRecs(Request req) {
        TreeSet<Record> issuerRecs = recs.get(req.getBook());
        if (issuerRecs == null) {
            issuerRecs = new TreeSet<>();
        }
        return issuerRecs;
    }


    class Record implements Comparable<Record> {
        public int sell;
        public Double price;
        public int buy;

        public Record(Integer sell, Double price, Integer buy) {
            this.sell = sell;
            this.price = price;
            this.buy = buy;
        }

        @Override
        public int compareTo(Record o) {
            return this.price.compareTo(o.price);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Record record = (Record) o;
            return Objects.equals(price, record.price);
        }

        @Override
        public int hashCode() {
            return Objects.hash(price);
        }
    }
}
