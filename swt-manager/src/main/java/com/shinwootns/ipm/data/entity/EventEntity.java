package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer event_id;
	private Timestamp collect_time;
	private Integer device_id;
	private String event_type;
	private String host_ip;
	private String message;
	private Integer severity;

	public EventEntity() {
	}

	public Integer getEventId() {
		return this.event_id;
	}

	public void setEventId(Integer eventId) {
		this.event_id = eventId;
	}

	public Timestamp getCollectTime() {
		return this.collect_time;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collect_time = collectTime;
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getEventType() {
		return this.event_type;
	}

	public void setEventType(String eventType) {
		this.event_type = eventType;
	}

	public String getHostIp() {
		return this.host_ip;
	}

	public void setHostIp(String hostIp) {
		this.host_ip = hostIp;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSeverity() {
		return this.severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

}