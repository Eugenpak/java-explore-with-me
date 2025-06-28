package ru.practicum.ewm.main.event.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventNewDto;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.dto.EventUpdateDto;
import ru.practicum.ewm.main.event.entity.enums.EventSort;
import ru.practicum.ewm.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.request.dto.ParticipationRequestDto;

import java.util.List;
import java.util.Map;

public interface EventService {
    List<EventShortDto> getAllEvents(Long userId, int from, int size);

    EventFullDto createEvent(Long userId, EventNewDto eventRequestDto);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, EventUpdateDto eventUpdateDto);

    List<ParticipationRequestDto> getRequestsByEventId(Long userId, Long eventId);

    Map<String, List<ParticipationRequestDto>> approveRequests(Long userId, Long eventId,
                                                               EventRequestStatusUpdateRequest requestUpdateDto);

    List<EventFullDto> getAllByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                     String rangeStart, String rangeEnd, int from, int size);

    EventFullDto approveEventByAdmin(Long eventId, EventUpdateDto eventUpdateDto);

    List<EventShortDto> getAllPublic(String text, List<Long> categories, Boolean paid,
                                     String rangeStart, String rangeEnd, boolean onlyAvailable,
                                     EventSort sort, int from, int size,
                                     HttpServletRequest request);

    EventFullDto getEventByIdPublic(Long eventId, HttpServletRequest request);
}
