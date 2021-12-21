import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import fourinarow.classes.PuissanceN;
import fourinarow.classes.PuissanceN.InvalidSizeException;

@Service
class TestPuissanceN {
	
	@Test
	void creation11() {
		assertThrows(InvalidSizeException.class, () -> {
	        new PuissanceN(11);
	    });
	}
	
	@Test
	void creation0() {
		assertThrows(InvalidSizeException.class, () -> {
	        new PuissanceN(2);
	    });
	}
	
	@Test
	void creation4() throws InvalidSizeException {
		PuissanceN game = new PuissanceN(4);
		Assert.assertEquals(7,game.getWidth());
		Assert.assertEquals(6,game.getHeight());
		Assert.assertEquals(7,game.getGrid().length);
		Assert.assertEquals(6,game.getGrid()[0].length);
	}
}

