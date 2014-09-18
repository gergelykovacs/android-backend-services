package com.backend.services;

import com.backend.services.ServiceData;
import java.util.List;

interface StorageServiceCall {

	List<ServiceData> storageCall(in String event, in List<ServiceData> data);
}