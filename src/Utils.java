import java.util.Random;

class Utils {
    static String getRandomString() {
        int leftLimitUC = 65;
        int rightLimitUC = 90;
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        int targetStringLength = random.nextInt(11) + 5;
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedIntUC = leftLimitUC + (int)(random.nextFloat() * (rightLimitUC - leftLimitUC + 1));
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));

            if (random.nextBoolean())
                buffer.append((char) randomLimitedInt);
            else
                buffer.append((char) randomLimitedIntUC);
        }

        return buffer.toString();
    }
}
