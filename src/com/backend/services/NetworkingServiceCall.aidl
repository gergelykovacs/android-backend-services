package com.backend.services;

import com.backend.services.ServiceData;
import java.util.List;

interface NetworkingServiceCall {

	List<ServiceData> networkingCall(in String event, in List<ServiceData> data);
}