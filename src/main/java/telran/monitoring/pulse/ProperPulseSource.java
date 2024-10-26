package telran.monitoring.pulse;

import java.util.Random;

public class ProperPulseSource {
	
	private static final int MIN_PULSE_VALUE = 50;
	private static final int MAX_PULSE_VALUE = 210;
	private static final int JUMP_PROBABILITY = 50;
	private static final int JUMP_POSITIVE_PROBABILITY = 50;
	private static final int MIN_JUMP_PERCENT  = 5;
	private static final int MAX_JUMP_PERCENT = 15;
	private static final int MAX_ERROR_PULSE_PROB = 5;
	private static final int MIX_ERROR_PULSE_PROB = 3;
	
	private static Random random = new Random();
	
	int currentPulse = random.nextInt(MIN_PULSE_VALUE, MAX_PULSE_VALUE + 1);
			
	private static boolean getTrueWithProbabilityOf( int percent ) {
		return random.nextInt( 100 ) < percent;
	}
	
	int getPulse() {
		int result = 0;
		if ( getTrueWithProbabilityOf(MIX_ERROR_PULSE_PROB)) {
			result = random.nextInt(MIN_PULSE_VALUE - 10);
		} else if (getTrueWithProbabilityOf(MAX_ERROR_PULSE_PROB)) {
			result = random.nextInt(MAX_PULSE_VALUE + 10, Integer.MAX_VALUE);
		} else {
			if ( getTrueWithProbabilityOf(JUMP_PROBABILITY)) {
				result = currentPulse = getNewPulseValue();
			} else {
				result = currentPulse;
			}
		}
		return result;
	}

	private int getNewPulseValue() {
		int delta = (int) Math.round(currentPulse * random.nextInt(MIN_JUMP_PERCENT, MAX_JUMP_PERCENT + 1) / 100.0);
		return  normalize( currentPulse + (getTrueWithProbabilityOf(JUMP_POSITIVE_PROBABILITY) ? delta : -delta));
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
