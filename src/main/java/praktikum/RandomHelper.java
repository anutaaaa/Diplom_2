package praktikum;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomUtils;

public class RandomHelper {

    public static User getRandomUserWithAllParams(){
        return new User(RandomHelper.getRandomName(), RandomHelper.getRandomEmail(),RandomHelper.getRandomPassword());
    }
    public static UserUpdate getRandomUserWithAllParamsNew(){
        return new UserUpdate(RandomHelper.getRandomName(), RandomHelper.getRandomEmail(),RandomHelper.getRandomPassword());
    }
    public static User getRandomWithoutName(){
        return new User(null, RandomHelper.getRandomEmail(),RandomHelper.getRandomPassword());
    }

    public static User getRandomUserWithoutEmail(){
        return new User(RandomHelper.getRandomName(), null,RandomHelper.getRandomPassword());
    }

    public static User getRandomUserWithoutPassword(){
        return new User(RandomHelper.getRandomName(), RandomHelper.getRandomEmail(),null);
    }

    public static String getRandomName(){
        Faker faker = new Faker();
        return faker.address().firstName();
    }
    public static String getRandomEmail(){
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword(){
        Faker faker = new Faker();
        return faker.internet().password(8,16,true,true,true);
    }

    public static int sizeForIngredientList(int max) {
        int listIndex = RandomUtils.nextInt(1, max);
        return listIndex;
    }
}
