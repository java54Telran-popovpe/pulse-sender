package telran.monitoring.pulse;
import java.net.*;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import telran.monitoring.pulse.dto.SensorData;
public class PulseSenderAppl {
private static final int N_PACKETS = 200;
private static final long TIMEOUT = 200;
private static final int N_PATIENTS = 10;
private static final String HOST = "3.25.232.141";
private static final int PORT = 5000;
private static Random random = new Random();
private static ProperPulseSource[] patients;
static DatagramSocket socket;
	public static void main(String[] args) throws Exception{
		patients = Stream.generate(ProperPulseSource::new).limit(N_PATIENTS).toArray(ProperPulseSource[]::new);
		socket = new DatagramSocket();
		IntStream.rangeClosed(1, N_PACKETS)
		.forEach(PulseSenderAppl::sendPulse);

	}
	static void sendPulse(int seqNumber) {
		SensorData data = getRandomSensorData(seqNumber);
		String jsonData = data.toString();
		sendDatagramPacket(jsonData);
		try {
			Thread.sleep(TIMEOUT);
		} catch (InterruptedException e) {
			
		}
	}
	private static void sendDatagramPacket(String jsonData) {
		byte [] buffer = jsonData.getBytes();
		try {
			DatagramPacket packet =
					new DatagramPacket(buffer, buffer.length,
							InetAddress.getByName(HOST), PORT);
			socket.send(packet);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	private static SensorData getRandomSensorData(int seqNumber) {
		
		long patientId = random.nextInt(1, N_PATIENTS + 1);
		int value = getRandomPulseValue(patientId);
		return new SensorData(seqNumber, patientId, value, System.currentTimeMillis());
	}
	private static int getRandomPulseValue(long patientId) {
		return patients[(int)patientId - 1].getPulse();
	}

}