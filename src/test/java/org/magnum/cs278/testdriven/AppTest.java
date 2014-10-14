package org.magnum.cs278.testdriven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class AppTest {

	private App app = new App();

	@Test
	public void testGetThreeThingsToDo() throws Exception {
		List<Event> whatToDo = app.getThreeThingsToDo();
		assertEquals(3, whatToDo.size());

		DateTime today = DateTime.now();

		for (Event thingToDo : whatToDo) {
			assertNotNull(thingToDo);
			assertNotNull(thingToDo.getDate());

			try {
				DateTime eventDate = Event.DATE_TIME_FORMAT
						.parseDateTime(thingToDo.getDate());
				assertTrue(eventDate.isAfter(today));
			} catch (IllegalArgumentException arg) {
				// The data in data.nashville.gov is..unfortunately...not
				// perfectly clean and we have to ignore the garbage...
			}
		}
	}

	@Test
	public void testGetParkSpecialPermits() throws Exception {
		List<Event> events = app.getParkSpecialPermits();
		assertTrue(events.size() > 0);
		for (Event event : events) {
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
		}
	}

	@Test
	public void testGetEventsInJune() throws Exception {
		List<Event> events = app.getEventsInJune();
		for (Event event : events) {
			assertTrue(event.getMonth().toLowerCase().contains("jun"));
		}
	}

	@Test
	public void testAttendanceGreaterThanFive() throws Exception {
		List<Event> events = app.AttendanceGreaterThanFive();

		for (Event event : events) {
			assertTrue(Integer.parseInt(event.getAttendance()) > 5);
		}
	}

	@Test
	public void testGetFirstEventOfMonth() throws Exception {
		String month = "Feb-2014";
		String testEventName = "Cupid's Chase";

		Event first = app.getFirstEventOfMonth(month);

		assertTrue(first.getName().equals(testEventName));
	}

	@Test
	public void testGetParkSpecialPermitsByAttendance() throws Exception {
		List<Event> events = app.getParkSpecialPermitsByAttendance();
		assertTrue(events.size() > 0);
		boolean sorted = true;
		double last = Double.POSITIVE_INFINITY;
		for (Event event : events) {
			if (Double.parseDouble(event.getAttendance()) > last) {
				sorted = false;
			} else {
				last = Double.parseDouble(event.getAttendance());
			}
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
		}
		assertTrue(sorted);
	}

	@Test
	public void testForLocation() throws Exception {

		List<Event> events = app.checkLocation("East Park");
		assertTrue(events.size() > 0);

		for (Event event : events) {
			assertTrue(event.getLocation().equals("East Park"));
		}

		events = app.checkLocation("Riverfront Park");
		assertTrue(events.size() > 0);

		for (Event event : events) {
			assertTrue(event.getLocation().equals("Riverfront Park"));
		}

		events = app.checkLocation("San Francisco");

		DateTime today = DateTime.now();

		for (Event event : events) {
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
			assertEquals(event.getLocation(), "San Francisco");
		}
	}

	@Test
	public void testGetEventsForMonth() throws Exception {

		List<Event> events = app.getEventsForMonth("Jan-2014");
		assertTrue(events.size() == 1);
		assertEquals("Jan-2014", events.get(0).getMonth());
	}

	@Test
	public void testGetEventsLargerThan() throws Exception {
		List<Event> events = app.getEventsLargerThan(1000);
		for (Event event : events)
			assertTrue(Integer.parseInt(event.getAttendance()) > 1000);
	}

	@Test
	public void testGetAllEventsInMonth() throws Exception {
		List<Event> evts = app.getAllEventsInMonth("january");
		for (Event e : evts) {
			assertTrue(e.getMonth().toLowerCase().equals("january"));
		}
	}	
	
	@Test
	public void testgetRiverfrontParkSpecialPermits() throws Exception {
		List<Event> events = app.getRiverfrontParkSpecialPermits();
		for (Event event : events) {
			assertTrue(event.getLocation().toLowerCase().equals("riverfront park"));
		}
	}
}
