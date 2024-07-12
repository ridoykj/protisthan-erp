package com.itbd.protisthan.others;

import org.junit.jupiter.api.Test;

public class TestProject {
    @Test
    public void test() {
        int[] nums = {1, 2, 3, 4, 5};
        int target = 8;
       findSum(nums, target);
    }

    private void findSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    System.out.println("[" + i + ", " + j + "]");
                }
            }
        }
    }
}
