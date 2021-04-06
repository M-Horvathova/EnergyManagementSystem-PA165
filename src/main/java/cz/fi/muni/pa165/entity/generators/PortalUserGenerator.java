package cz.fi.muni.pa165.entity.generators;

import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.enums.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Martin Podhora
 */
public class PortalUserGenerator {
    private static final int SEED = 1;

    private static final List<String> firstNames = new ArrayList<String>() {{
        add("James");
        add("Mary");
        add("John");
        add("Patricia");
        add("Robert");
        add("Jennifer");
    }};

    private static final List<String> lastNames = new ArrayList<String>() {{
        add("Smith");
        add("Johnson");
        add("Williams");
        add("Brown");
        add("Jones");
        add("Miller");
    }};

    private static final List<String> emailProviders = new ArrayList<String>() {{
        add("gmail.com");
        add("yahoo.com");
        add("aol.com");
        add("zoho.com");
        add("mail.com");
        add("protonmail.com");
    }};

    private static final List<String> passwords = new ArrayList<String>() {{
        add("123456");
        add("abcdef");
        add("654321");
        add("fedcba");
        add("000000");
        add("hello");
    }};

    private static final List<String> phones = new ArrayList<String>() {{
        add("+123456789");
        add("+234567891");
        add("+345678912");
        add("+456789123");
        add("+567891234");
        add("+678912345");
    }};

    private static String getRandomFirstName(Random rng) {
        return firstNames.get(rng.nextInt(firstNames.size()));
    }

    private static String getRandomLastName(Random rng) {
        return lastNames.get(rng.nextInt(lastNames.size()));
    }

    private static String getRandomEmail(String firstName, String lastName, Random rng) {
        return firstName + "." + lastName + "@" + emailProviders.get(rng.nextInt(emailProviders.size()));
    }

    private static String getRandomPassword(Random rng) {
        return passwords.get(rng.nextInt(passwords.size()));
    }

    private static String getRandomPhone(Random rng) {
        return phones.get(rng.nextInt(phones.size()));
    }

    public static PortalUser generateNewUser() {
        Random rng = new Random(SEED);
        PortalUser pu = new PortalUser();
        pu.setFirstName(getRandomFirstName(rng));
        pu.setLastName(getRandomLastName(rng));
        pu.setEmail(getRandomEmail(pu.getFirstName(), pu.getLastName(), rng));
        pu.setPasswordHash(getRandomPassword(rng));
        pu.setPhone(getRandomPhone(rng));
        pu.setUserRole(UserRole.user);
        pu.setCreatedTimestamp(LocalDateTime.now());
        return pu;
    }
}
