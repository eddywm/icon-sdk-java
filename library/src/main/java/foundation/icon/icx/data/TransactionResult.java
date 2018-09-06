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
 */

package foundation.icon.icx.data;

import foundation.icon.icx.transport.jsonrpc.RpcItem;
import foundation.icon.icx.transport.jsonrpc.RpcObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/icon-project/icon-rpc-server/blob/develop/docs/icon-json-rpc-v3.md#icx_gettransactionresult" target="_blank">ICON JSON-RPC API</a>
 */
public class TransactionResult {

    private RpcObject properties;

    TransactionResult(RpcObject properties) {
        this.properties = properties;
    }

    /**
     * @return 1 on success, 0 on failure.
     */
    public BigInteger getStatus() {
        RpcItem item = properties.getItem("status");
        return item != null ? item.asInteger() : null;
    }

    /**
     * @return Recipient address of the transaction
     */
    public String getTo() {
        RpcItem item = properties.getItem("to");
        return item != null ? item.asString() : null;
    }

    /**
     * @return Transaction hash
     */
    public Bytes getTxHash() {
        RpcItem item = properties.getItem("txHash");
        return item != null ? item.asBytes() : null;
    }

    /**
     * @return Transaction index in the block
     */
    public BigInteger getTxIndex() {
        RpcItem item = properties.getItem("txIndex");
        return item != null ? item.asInteger() : null;
    }

    /**
     * @return Height of the block that includes the transaction.
     */
    public BigInteger getBlockHeight() {
        RpcItem item = properties.getItem("blockHeight");
        return item != null ? item.asInteger() : null;
    }

    /**
     * @return Hash of the block that includes the transation.
     */
    public Bytes getBlockHash() {
        RpcItem item = properties.getItem("blockHash");
        return item != null ? item.asBytes() : null;
    }

    /**
     * @return Sum of stepUsed by this transaction and all preceeding transactions in the same block.
     */
    public BigInteger getCumulativeStepUsed() {
        RpcItem item = properties.getItem("cumulativeStepUsed");
        return item != null ? item.asInteger() : null;
    }

    /**
     * @return The amount of step used by this transaction.
     */
    public BigInteger getStepUsed() {
        RpcItem item = properties.getItem("stepUsed");
        return item != null ? item.asInteger() : null;
    }

    /**
     * @return The step price used by this transaction.
     */
    public BigInteger getStepPrice() {
        RpcItem item = properties.getItem("stepPrice");
        return item != null ? item.asInteger() : null;
    }

    /**
     * @return SCORE address if the transaction created a new SCORE.
     */
    public String getScoreAddress() {
        RpcItem item = properties.getItem("scoreAddress");
        return item != null ? item.asString() : null;
    }

    /**
     * @return Bloom filter to quickly retrieve related eventlogs.
     */
    public String getLogsBloom() {
        RpcItem item = properties.getItem("logsBloom");
        return item != null ? item.asString() : null;
    }

    /**
     * @return List of event logs, which this transaction generated.
     */
    public List<EventLog> getEventLogs() {
        RpcItem item = properties.getItem("eventLogs");
        List<EventLog> eventLogs = new ArrayList<>();
        if (item != null) {
            for (RpcItem rpcItem : item.asArray()) {
                eventLogs.add(new EventLog(rpcItem.asObject()));
            }
        }
        return eventLogs;
    }

    /**
     * @return This field exists when status is 0. Contains code(str) and message(str).
     */
    public Failure getFailure() {
        RpcItem item = properties.getItem("failure");
        return item != null ? new Failure(item.asObject()) : null;
    }

    @Override
    public String toString() {
        return "TransactionResult{" +
                "properties=" + properties +
                '}';
    }

    public class EventLog {
        private RpcObject properties;

        EventLog(RpcObject properties) {
            this.properties = properties;
        }

        public String getScoreAddress() {
            RpcItem item = properties.getItem("scoreAddress");
            return item != null ? item.asString() : null;
        }

        public List<RpcItem> getIndexed() {
            RpcItem item = properties.getItem("indexed");
            return item != null ? item.asArray().asList() : null;
        }

        public List<RpcItem> getData() {
            RpcItem field = properties.getItem("data");
            return field != null ? field.asArray().asList() : null;
        }

        @Override
        public String toString() {
            return "EventLog{" +
                    "properties=" + properties +
                    '}';
        }
    }

    public static class Failure {
        private RpcObject properties;

        private Failure(RpcObject properties) {
            this.properties = properties;
        }

        public BigInteger getCode() {
            RpcItem item = properties.getItem("code");
            return item != null ? item.asInteger() : null;
        }

        public String getMessage() {
            RpcItem item = properties.getItem("message");
            return item != null ? item.asString() : null;
        }

        @Override
        public String toString() {
            return "Failure{" +
                    "properties=" + properties +
                    '}';
        }
    }
}
