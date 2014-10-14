package org.magnum.cs278.testdriven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
			assertTrue(event.getMonth().toLowerCase().contains("jun"));
		}
	}

	@Test
	public void testAttendanceGreaterThanFive() throws Exception {
		List<Event> events = app.AttendanceGreaterThanFive();
		assertTrue(events.size() > 0);
		
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
			
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
			
			if (Double.parseDouble(event.getAttendance()) > last) {
				sorted = false;
			} else {
				last = Double.parseDouble(event.getAttendance());
			}
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
	}
	
	@Test
	public void testEventFieldsNotNull() throws Exception {
		
		List<Event> events = app.checkLocation("San Francisco");
		assertTrue(events.size() >= 0);
		
		for(Event event: events){
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
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
		assertTrue(evts.size() >= 0);
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
	
	@Test
	public void testGetEventsWithLocation() throws Exception {
		List<Event> events = app.getEventsWithLocation("Percy Warner Park");
		assertTrue(events.size() > 0);
		for (Event event: events)
		{
			assertNotNull(event);
			assertNotNull(event.getLocation());
			assertNotNull(event.getName());
			assertNotNull(event.getAttendance());
			assertNotNull(event.getDate());
			assertTrue(event.getLocation().equals("Percy Warner Park"));
		}
		List<Event> evts = app.getParkSpecialPermits();
		List<Event> evtsAtLocation = new ArrayList<Event>();
		for (Event evt : evts) {
			if (evt.getLocation().equals("Percy Warner Park")) {
				evtsAtLocation.add(evt);
			}
		}
		for (int i = 0; i < events.size(); i++)
		{
			assertTrue(events.get(i).getName().equals(evtsAtLocation.get(i).getName()));
			assertTrue(events.get(i).getDate().equals(evtsAtLocation.get(i).getDate()));
			assertTrue(events.get(i).getAttendance().equals(evtsAtLocation.get(i).getAttendance()));
		}
	}
}
