package ru.job4j.stockmarket;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование StockMarket
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StockMarketTest {

    StockMarket stockMarket;

    @Before
    public void setUp() {
        stockMarket = new StockMarket();
    }

    @Test
    public void whenAddExistingPriceForBidWhenVolumeHasSum() {
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 8, 2));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 8, 3));
        final String formatString = "%20s | %20s | %20s\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, "buy", "price", "sell"));
        sb.append(String.format(formatString, "0", "8.0", "5"));
        assertThat(sb.toString(), is(stockMarket.getIssuerInfo("Газпром")));
    }

    @Test
    public void whenAddExistingPriceForAskWhenVolumeHasSum() {
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 8, 2));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 8, 3));
        final String formatString = "%20s | %20s | %20s\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, "buy", "price", "sell"));
        sb.append(String.format(formatString, "5", "8.0", "0"));
        assertThat(sb.toString(), is(stockMarket.getIssuerInfo("Газпром")));
    }

    @Test
    public void whenAddAskRequestAndSellRecordsExistThenSellRecordsDecrease() {
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 8, 2));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 9, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 10, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 9, 4));
        final String formatString = "%20s | %20s | %20s\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, "buy", "price", "sell"));
        sb.append(String.format(formatString, "0", "9.0", "1"));
        sb.append(String.format(formatString, "0", "10.0", "3"));
        assertThat(sb.toString(), is(stockMarket.getIssuerInfo("Газпром")));
    }

    @Test
    public void whenAddAskRequestMoreThanSellRecordsExistThenSellRecordsDeleteAndRemainBuyRecord() {
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 8, 2));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 9, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 10, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 9, 6));
        final String formatString = "%20s | %20s | %20s\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, "buy", "price", "sell"));
        sb.append(String.format(formatString, "1", "9.0", "0"));
        sb.append(String.format(formatString, "0", "10.0", "3"));
        assertThat(sb.toString(), is(stockMarket.getIssuerInfo("Газпром")));
    }

    @Test
    public void whenAddBidRequestAndBuyRecordsExistThenBuyRecordsDecrease() {
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 8, 2));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 9, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 10, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 9, 4));
        final String formatString = "%20s | %20s | %20s\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, "buy", "price", "sell"));
        sb.append(String.format(formatString, "2", "8.0", "0"));
        sb.append(String.format(formatString, "2", "9.0", "0"));
        assertThat(sb.toString(), is(stockMarket.getIssuerInfo("Газпром")));
    }

    @Test
    public void whenAddBidRequestAndSellMoreThanSellRecordsExistThenSellRecordsDeleteAndRemainBuyRecord() {
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 8, 2));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 9, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.ASK, 10, 3));
        stockMarket.exec(new Request("Газпром", RequestType.ADD, RequestAction.BID, 9, 7));
        final String formatString = "%20s | %20s | %20s\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatString, "buy", "price", "sell"));
        sb.append(String.format(formatString, "2", "8.0", "0"));
        sb.append(String.format(formatString, "0", "9.0", "1"));
        assertThat(sb.toString(), is(stockMarket.getIssuerInfo("Газпром")));
    }

}