package com.galvanize;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZipCodeProcessorTest {

    @Test
    public void testCustomExceptions() {
        InvalidFormatException invalidFormatException = new InvalidFormatException("Hello");
        assertEquals("Hello", invalidFormatException.getMessage());

        NoServiceException noServiceException = new NoServiceException("No Service");
        assertEquals("No Service", noServiceException.getMessage());
    }

    @Test
    public void testVerifier() {
        Verifier verifier = new Verifier();

        Exception tooLong = assertThrows(Exception.class, () -> verifier.verify("345678"));
        Exception tooShort = assertThrows(Exception.class, () -> verifier.verify("2345"));
        Exception startsWithOne = assertThrows(Exception.class, () -> verifier.verify("12345"));

        assertEquals("ERRCODE 21: INPUT_TOO_LONG", tooLong.getMessage());
        assertEquals("ERRCODE 22: INPUT_TOO_SHORT", tooShort.getMessage());
        assertEquals("ERRCODE 27: NO_SERVICE", startsWithOne.getMessage());
    }

    @Test
    public void testProcessMethod() {
        ZipCodeProcessor processor = new ZipCodeProcessor(new Verifier());

        assertEquals("Thank you! Your package will arrive soon.", processor.process("23456"));

        InvalidFormatException tooLong = assertThrows(InvalidFormatException.class, () -> processor.process("647586"));
        InvalidFormatException tooShort = assertThrows(InvalidFormatException.class, () -> processor.process("3456"));
        assertEquals("The zip code you entered was the wrong length.", tooLong.getMessage());
        assertEquals("The zip code you entered was the wrong length.", tooShort.getMessage());

        NoServiceException startsWithOne = assertThrows(NoServiceException.class, () -> processor.process("12345"));
        assertEquals("We're sorry, but the zip code you entered is out of our range.", startsWithOne.getMessage());
    }

}