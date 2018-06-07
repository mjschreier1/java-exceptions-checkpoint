package com.galvanize;

public class ZipCodeProcessor {

    // don't alter this code...
    private final Verifier verifier;

    public ZipCodeProcessor(Verifier verifier) {
        this.verifier = verifier;
    }

    public String process(String s) {
        try {
            verifier.verify(s);
            return "Thank you! Your package will arrive soon.";
        } catch (InvalidFormatException e) {
            return "The zip code you entered was the wrong length.";
        } catch (NoServiceException e) {
            return "We're sorry, but the zip code you entered is out of our range.";
        }
    }

    // write your code below here...

}

