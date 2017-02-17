package pietimer;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class PieTimer {
	private static final int PIE_HOUR = 12;
	private static final int PIE_MINUTE = 0;
	private static final DayOfWeek PIE_DAY = DayOfWeek.FRIDAY;

	private long daysToPie;
	private long hoursToPie;
	private long minutesToPie;

	public static void main(String[] args) throws IOException {
		PieTimer timer = new PieTimer();
		timer.say();
	}

	public PieTimer() throws IOException {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime pieTime = now.withHour(PIE_HOUR).withMinute(PIE_MINUTE).withSecond(0);
		if (now.getDayOfWeek() != PIE_DAY || now.getHour() > PIE_HOUR) {
			pieTime = pieTime.with(TemporalAdjusters.next(PIE_DAY));
		}
		daysToPie = now.until(pieTime, ChronoUnit.DAYS);
		hoursToPie = now.plus(daysToPie, ChronoUnit.DAYS).until(pieTime, ChronoUnit.HOURS);
		minutesToPie = now.plus(daysToPie, ChronoUnit.DAYS).plus(hoursToPie, ChronoUnit.HOURS).until(pieTime,
				ChronoUnit.MINUTES);
	}

	private void say() throws IOException {
		String toSay = "";
		if (daysToPie > 0) {
			toSay += daysToPie + " days, ";
		}
		if (hoursToPie > 0) {
			toSay += hoursToPie + " hours and ";
		}
		toSay += minutesToPie + "minutes to pie";
		ArrayList<String> commands = new ArrayList<String>();
		String os = System.getProperty("os.name");
		if (os.contains("Linux")) {
			commands.add("espeak");
			commands.add("-v");
			commands.add("en-scottish");
			commands.add("-s");
			commands.add("140");
			commands.add("-p");
			commands.add("60");
		} else if (os.contains("Mac")) {
			commands.add("say");
		}
		commands.add(toSay);
		new ProcessBuilder().command(commands).start();
	}
}
