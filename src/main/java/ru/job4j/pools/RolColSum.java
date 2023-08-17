package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class RolColSum {

    public static class Sums {

        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        public static Sums[] sum(int[][] matrix) {
            Sums[] rsl = new Sums[matrix.length];
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    rowSum += matrix[i][j];
                    colSum += matrix[j][i];
                }
                rsl[i] = new Sums(rowSum, colSum);
                rowSum = 0;
                colSum = 0;
            }
            return rsl;
        }

        public static Sums[] asyncSum(int[][] matrix) {
            List<CompletableFuture<Sums>> list = new ArrayList<>();
            for (int i = 0; i < matrix.length; i++) {
                int num = i;
                list.add(
                        CompletableFuture.supplyAsync(() -> {
                            int rowSum = 0;
                            int colSum = 0;
                            for (int j = 0; j < matrix.length; j++) {
                                rowSum += matrix[num][j];
                                colSum += matrix[j][num];
                            }
                            return new Sums(rowSum, colSum);
                        })
                );
            }
            return list.stream().map(CompletableFuture::join).toArray(Sums[]::new);
        }

    }

}
