package ru.job4j.utils.math;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ExpressionParserTest {
    private ExpressionParser parser = new ExpressionParser();

    //region #Adding
    @Test
    public void whenTwoAddTwoShouldFour() {
        assertThat(parser.operation("2", "2", "+"), is("4"));
    }

    @Test
    public void whenThreeAddThreeShouldSix() {
        assertThat(parser.operation("3", "3", "+"), is("6"));
    }

    @Test
    public void whenOneFractionFourteenAddOneFractionTwentySixShouldTenFractionNinetyOne() {
        assertThat(parser.operation("1/14", "1/26", "+"), is("10/91"));
    }
    //endregion

    //region #Subtracting
    @Test
    public void whenTwoSubtractOneShouldOne() {
        assertThat(parser.operation("2", "1", "-"), is("1"));
    }

    @Test
    public void whenTwoSubtractOneFractionThreeShouldFiveFractionThree() {
        assertThat(parser.operation("2", "1/3", "-"), is("5/3"));
    }

    @Test
    public void whenOneFractionTwoSubtractOneFractionThreeShouldOneFractionSix() {
        assertThat(parser.operation("1/2", "1/3", "-"), is("1/6"));
    }
    //endregion

    //region #Multiply
    @Test
    public void whenTwoMultiplyTwoShouldFour() {
        assertThat(parser.operation("2", "2", "*"), is("4"));
    }

    @Test
    public void whenThreeMultiplyThreeShouldNine() {
        assertThat(parser.operation("3", "3", "*"), is("9"));
    }

    @Test
    public void whenOneFractionTwoMultiplyFourShouldTwo() {
        assertThat(parser.operation("1/2", "4", "*"), is("2"));
    }

    @Test
    public void whenFiveFractionSixMultiplyFourFractionFiveShouldTwoFractionThree() {
        assertThat(parser.operation("5/6", "4/5", "*"), is("2/3"));
    }
    //endregion

    //region #Divide
    @Test
    public void whenTwoDivideTwoShouldOne() {
        assertThat(parser.operation("2", "2", "/"), is("1"));
    }

    @Test
    public void whenOneDivideThreeShouldOneThird() {
        assertThat(parser.operation("1", "3", "/"), is("1/3"));
    }

    @Test
    public void whenTwoDivideSixShouldOneThrird() {
        assertThat(parser.operation("2", "6", "/"), is("1/3"));
    }

    @Test
    public void whenEightDivideTenShouldOneThrird() {
        assertThat(parser.operation("8", "10", "/"), is("4/5"));
    }

    @Test
    public void whenSixDivideSixShouldOne() {
        assertThat(parser.operation("6", "6", "/"), is("1"));
    }

    @Test
    public void whenSixDivideTwoShouldThree() {
        assertThat(parser.operation("6", "2", "/"), is("3"));
    }

    @Test
    public void whenSixDivideOneFractionTwoShouldTwelve() {
        assertThat(parser.operation("6", "1/2", "/"), is("12"));
    }

    @Test
    public void whenOneFractionThreeDivideOneFractionSixShouldTwo() {
        assertThat(parser.operation("1/3", "1/6", "/"), is("2"));
    }
    //endregion

    //region #Least Common Multiple
    @Test
    public void whenLeastCommonMultipleM18And4Should36() {
        assertThat(parser.leastCommonMultiple(18, 4), is(36));
    }

    @Test
    public void whenLeastCommonMultiple14And26Should182() {
        assertThat(parser.leastCommonMultiple(14, 26), is(182));
    }
    //endregion

    //region #Shrink/Expand fraction
    @Test
    public void whenShrinkEightFractionTenShouldFourFractionFive() {
        assertThat(parser.shrinkFraction("8/10"), is("4/5"));
    }

    @Test
    public void whenShrinkEightFractionFourShouldTwo() {
        assertThat(parser.shrinkFraction("8/4"), is("2"));
    }

    @Test
    public void whenExpandOneByThreeShouldThreeFractionThree() {
        assertThat(parser.expandToFraction("1", 3), is("3/3"));
    }

    @Test
    public void whenExpandOneFractionThreeByFiveShouldFiveFractionFifthteen() {
        assertThat(parser.expandToFraction("1/3", 5), is("5/15"));
    }
    //endregion

    //region #Calculate
    @Test
    public void whenTwoPlusTwoShouldPostfixTwoTwoPlus() {
        assertThat(parser.infix2postfix("2+2"), is("2 2 +"));
    }

    @Test
    public void whenInfixBracketTwoPlusTwoMultiplyTwoBracketDivideThreeShouldPostfixTwoTwoTwoMultiplyAddThreeDiv() {
        assertThat(parser.infix2postfix("(2+2*2)/3"), is("2 2 2 * + 3 /"));
    }

    @Test
    public void whenCalcPostfixTwoTwoPlusShouldFour() {
        assertThat(parser.calcPostfix("2 2 +"), is(4.0));
    }

    @Test
    public void whenCalcPostfixTwoTwoTwoMultiplyAddThreeDivShouldTwo() {
        assertThat(parser.calcPostfix("2 2 2 * + 3 /"), is(2.0));
    }

    @Test
    public void whenCalcInfixTwoPlusTwoMultiplyTwoShouldSix() {
        assertThat(parser.calcInfix("2+2*2"), is(6.0));
    }

    @Test
    public void whenCalcInfixBracketTwoPlusTwoMultiplyTwoBracketDivideThreeShouldTwo() {
        assertThat(parser.calcInfix("(2+2*2)/3"), is(2.0));
    }

    @Test
    public void whenCalcInfixFourPlusBracketBracketOnePlusSevenBracketPlusEightBracketShouldTwenty() {
        assertThat(parser.calcInfix("4+((1+7)+8)"), is(20.0));
    }
    //endregion
}
