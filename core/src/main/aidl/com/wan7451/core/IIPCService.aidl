// IIPCService.aidl
package com.wan7451.core;

// Declare any non-default types here with import statements
import com.wan7451.core.IPCResponse;
import com.wan7451.core.IPCRequest;

interface IIPCService {
    IPCResponse send(in IPCRequest request);
}
