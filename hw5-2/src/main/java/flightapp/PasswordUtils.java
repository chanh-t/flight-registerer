package flightapp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 * A collection of utility methods to help with managing passwords
 */
public class PasswordUtils {
  /**
   * Generates a cryptographically-secure salted password.
   */
  public static byte[] hashPassword(String password) {
    byte[] salt = generateSalt();
    byte[] saltedHash = generateSaltedPassword(password, salt);

    // TODO: combine the salt and the salted hash into a single byte array that
    // can be written to the database
    byte[] ret = new byte[SALT_LENGTH + saltedHash.length];
    System.arraycopy(salt, 0, ret, 0, SALT_LENGTH);
    System.arraycopy(saltedHash, 0, ret, SALT_LENGTH, saltedHash.length);
    return ret;
  }

  /**
   * Verifies whether the plaintext password can be hashed to provided salted hashed password.
   */
  public static boolean plaintextMatchesHash(String plaintext, byte[] saltedHashed) {
    // TODO: extract the salt from the byte array (ie, undo the logic you implemented in 
    // generateSaltedPassword), then use the it to check whether the user-provided plaintext
    // password matches the password hash.
    byte[] salt = new byte[SALT_LENGTH];
    System.arraycopy(saltedHashed, 0, salt, 0, SALT_LENGTH);
    byte[] saltedPass = generateSaltedPassword(plaintext, salt);

    byte[] genPass = new byte[SALT_LENGTH + saltedPass.length];
    System.arraycopy(salt, 0, genPass, 0, SALT_LENGTH);
    System.arraycopy(saltedPass, 0, genPass, SALT_LENGTH, saltedPass.length);

    return Arrays.equals(genPass, saltedHashed);
  }
  
  // Password hashing parameter constants.
  private static final int HASH_STRENGTH = 65536;
  private static final int KEY_LENGTH = 128;
  private static final int SALT_LENGTH = 16;

  /**
   * Generate a small bit of randomness to serve as a password "salt"
   */
  static byte[] generateSalt() {
    // TODO: implement this.
    byte[] salt = new byte[SALT_LENGTH];
    for (int i = 0; i < SALT_LENGTH; i++) {
      salt[i] = (byte) (Math.random() * 256);
    }
    return salt;
  }

  /**
   * Uses the provided salt to generate a cryptographically-secure hash of the provided password
   */
  static byte[] generateSaltedPassword(String password, byte[] salt)
    throws IllegalStateException {
    // Specify the hash parameters, including the salt
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                                  HASH_STRENGTH, KEY_LENGTH);

    // Hash the whole thing
    SecretKeyFactory factory = null;
    byte[] hash = null; 
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      hash = factory.generateSecret(spec).getEncoded();
      return hash;
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new IllegalStateException();
    }
  }

}
