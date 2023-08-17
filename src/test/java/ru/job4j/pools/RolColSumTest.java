package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void testSum() {
        RolColSum rolColSum = new RolColSum();
        int[][] matrix = {{1, 2, 3},
                        {2, 4, 5},
                        {3, 5, 6}};

        RolColSum.Sums[] expected = {new RolColSum.Sums(6, 6),
                                    new RolColSum.Sums(11, 11),
                                    new RolColSum.Sums(14, 14)};

       RolColSum.Sums[] rsl = RolColSum.Sums.sum(matrix);
       assertThat(rsl).isEqualTo(expected);
    }

    @Test
    void testAsyncSum() {
        RolColSum rolColSum = new RolColSum();
        int[][] matrix = {{1, 2, 3},
                {2, 4, 5},
                {3, 5, 6}};

        RolColSum.Sums[] expected = {new RolColSum.Sums(6, 6),
                new RolColSum.Sums(11, 11),
                new RolColSum.Sums(14, 14)};

        RolColSum.Sums[] rsl = RolColSum.Sums.asyncSum(matrix);
        assertThat(rsl).isEqualTo(expected);
    }

}