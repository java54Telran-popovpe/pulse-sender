package telran.monitoring.pulse;

import java.util.Random;

public class ProperPulseSource {
	
	private static final int MIN_PULSE_VALUE = 50;
	private static final int MAX_PULSE_VALUE = 200;
	private static final int JUMP_PROBABILITY = 40;
	private static final int JUMP_POSITIVE_PROBABILITY = 50;
	private static final int MIN_JUMP_PERCENT  = 2;
	private static final int MAX_JUMP_PERCENT = 15;
	
	private static Random random = new Random();
	
	int currentPulse = random.nextInt(MIN_PULSE_VALUE, MAX_PULSE_VALUE + 1 );
			
	private static boolean getTrueWithProbabilityOf( int percent ) {
		return random.nextInt( 100 ) < percent;
	}
	
	int getPulse() {
		int result = currentPulse;
		if ( getTrueWithProbabilityOf(JUMP_PROBABILITY)) {
			currentPulse = getNewPulseValue();
		}
		return result;
	}

	private int getNewPulseValue() {
		int delta = (int) Math.round(currentPulse * random.nextInt(MIN_JUMP_PERCENT, MAX_JUMP_PERCENT + 1) / 100.0);
		return normalize( currentPulse + (getTrueWithProbabilityOf(JUMP_POSITIVE_PROBABILITY) ? delta : -delta));
	}

	private static int normalize(int pulse) {
		int result = pulse;
		if ( pulse < MIN_PULSE_VALUE ) {
			result = MIN_PULSE_VALUE;
		} else if ( pulse > MAX_PULSE_VALUE ) {
			result = MAX_PULSE_VALUE;
		}
		return result;
	}

}
