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
	public void testGetMarchEvents2014() throws Exception {
		String month = "Mar-2014";
		String[] evts = new String[13];
		evts[0] = "MTPC";
		evts[1] = "Mardi Gras Y'all Street Festival";
		evts[2] = "Let's Get Richland Creek Clean!";
		evts[3] = "Tom King 1/2 Marathon";
		evts[4] = "St. Puppy's Day";
		evts[5] = "St. Patrick's Day Festival";
		evts[6] = "American Musical Salute Band Concert";
		evts[7] = "Arbor Day";
		evts[8] = "MTPC";
		evts[9] = "Music City Exchange Dance";
		evts[10] = "Nashville NEDA Walk";
		evts[11] = "The Greenways Marathon";
		evts[12] = "TN Kurdish Counsel Picnic";
		evts[13]= "Vanderbilt ZTA Think Pink 5K";
		evts[14]= "Puppies in the Park/Belmont Partnership";
		evts[15]= "Shelby Shootout Disc Golf";
		evts[16]= "Treehouse Racing/Bike Race";
		List<Event> marchEvents = app.getMarchEvents2014();
		assertTrue(marchEvents.size() == 17);
		for(int i = 0; i < 17; i++){
			assertTrue(marchEvents.get(i).getMonth() == month);
			assertTrue(marchEvents.get(i).getName() == evts[i]);
		}
	}
}