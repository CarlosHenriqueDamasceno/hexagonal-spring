package hexagonal.user.utils;

import hexagonal.user.domain.User;

public class UserTestUtils {

    public static String rightPassword = "12345678";
    public static String rightPasswordEncrypted = "7c222fb2927d828af22f592134e8932480637c0d";
    public static String invalidPassword = "12345";
    public static String validEmail = "carlos@teste.com";
    public static String invalidEmail = "carlos@teste";
    public static String editedEmail = "carloseditado@teste.com";

    public static User existentUser = User.buildExistentUser(
            1L,
            "Carlos",
            validEmail,
            rightPasswordEncrypted
    );

    public static User updatedUser = User.buildExistentUser(
            1L,
            "Carlos editado",
            editedEmail,
            rightPasswordEncrypted
    );

}
