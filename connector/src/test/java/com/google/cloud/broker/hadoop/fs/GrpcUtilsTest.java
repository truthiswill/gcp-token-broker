// Copyright 2019 Google LLC
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.cloud.broker.hadoop.fs;

import java.util.Base64;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import io.grpc.ManagedChannel;
import io.grpc.internal.testing.TestUtils;

import static org.junit.Assert.*;
import org.junit.Test;

import com.google.cloud.broker.protobuf.BrokerGrpc;


public class GrpcUtilsTest {

    @Test
    public void testManagedChannelAuthority() {
        ManagedChannel channel = GrpcUtils.newManagedChannel("testhost", 8888, false, "");
        assertEquals("testhost:8888", channel.authority());
    }

    @Test
    public void testManagedChannelTLSSuccess() {
        String certificate;
        try {
            X509Certificate[] trustedCaCerts = {
                TestUtils.loadX509Cert("ca.pem")
            };
            certificate =
                "-----BEGIN CERTIFICATE-----\n" +
                Base64.getEncoder().encodeToString(trustedCaCerts[0].getEncoded()) + "\n" +
                "-----END CERTIFICATE-----";
        } catch (CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
        ManagedChannel channel = GrpcUtils.newManagedChannel("testhost", 8888, true, certificate);
        // TODO: Verify that the certificate is correctly assigned to the channel
    }

    @Test
    public void testManagedChannelTLSInvalidCertificate() {
        try {
            GrpcUtils.newManagedChannel("testhost", 8888, true, "aaaa");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The provided certificate for the broker service is invalid", e.getMessage());
        }
    }

    @Test
    public void testNewStub() {
        ManagedChannel channel = GrpcUtils.newManagedChannel("testhost", 8888, false, "");
        BrokerGrpc.BrokerBlockingStub stub = GrpcUtils.newStub(channel);
        assertEquals(channel, stub.getChannel());
    }

}
