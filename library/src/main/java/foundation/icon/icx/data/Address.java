/*
 * Copyright 2018 ICON Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package foundation.icon.icx.data;

import foundation.icon.icx.crypto.IconKeys;
import org.web3j.utils.Numeric;

import java.util.Arrays;

public class Address {

    private AddressPrefix prefix;
    private byte[] body;
    private boolean isMalformed = false;
    private String malformedAddress;

    public static Address createMalformedAddress(String malformedAddress) {
        Address address = new Address();
        address.isMalformed = true;
        address.malformedAddress = malformedAddress;
        return address;
    }

    private Address() {
    }

    public Address(String address) {
        AddressPrefix prefix = IconKeys.getAddressHexPrefix(address);
        if (prefix == null) {
            throw new IllegalArgumentException("Invalid address prefix");
        } else if (!IconKeys.isValidAddress(address)) {
            throw new IllegalArgumentException("Invalid address");
        }

        this.prefix = prefix;
        this.body = getAddressBody(address);
    }

    public Address(AddressPrefix prefix, byte[] body) {
        if (!IconKeys.isValidAddressBody(body)) {
            throw new IllegalArgumentException("Invalid address");
        }

        this.prefix = prefix;
        this.body = body;
    }

    private byte[] getAddressBody(String address) {
        String cleanInput = IconKeys.cleanHexPrefix(address);
        return Numeric.hexStringToByteArray(cleanInput);
    }

    public AddressPrefix getPrefix() {
        return prefix;
    }

    public boolean isMalformed() {
        return isMalformed;
    }

    @Override
    public String toString() {
        if (isMalformed) {
            return malformedAddress;
        } else {
            return getPrefix().getValue() + Numeric.toHexStringNoPrefix(body);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Address) {
            Address other = (Address) obj;
            if (isMalformed) {
                return malformedAddress.equals(other.malformedAddress);
            } else {
                return !other.isMalformed && other.prefix == prefix && Arrays.equals(other.body, body);
            }
        }
        return false;
    }

    public enum AddressPrefix {

        EOA("hx"),
        CONTRACT("cx");

        private String prefix;

        AddressPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getValue() {
            return prefix;
        }

        public static AddressPrefix fromString(String prefix) {
            if (prefix != null) {
                for (AddressPrefix p : AddressPrefix.values()) {
                    if (prefix.equalsIgnoreCase(p.getValue())) {
                        return p;
                    }
                }
            }
            return null;
        }
    }

}
