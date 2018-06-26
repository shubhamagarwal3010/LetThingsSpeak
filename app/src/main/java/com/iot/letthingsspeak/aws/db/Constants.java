/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.iot.letthingsspeak.aws.db;

import com.amazonaws.regions.Regions;


public class Constants {


    public static final String IDENTITY_POOL_ID = "ap-south-1:9f293b03-7ce1-45c5-99e5-1b7ee44497c9";
    public static final Regions COGNITO_REGION = Regions.AP_SOUTH_1;  // Set your Cognito region if is different

    // Note that spaces are not allowed in the table name
    public static final Regions DYNAMODB_REGION = Regions.AP_SOUTH_1;  // Set your DynamoDB region if is different

    // Table Names
    public static final String USER_TABLE = "letthingsspeak-mobilehub-849318221-user";
    public static final String ROOM_TABLE = "letthingsspeak-mobilehub-849318221-room";
    public static final String GATEWAY_TABLE = "letthingsspeak-mobilehub-849318221-gateway";
    public static final String DEVICE_TABLE = "letthingsspeak-mobilehub-849318221-device";
    public static final String USER_ROOM_TABLE = "letthingsspeak-mobilehub-849318221-userRoom";


    public enum DynamoDBManagerType {
        GET_TABLE_STATUS, INSERT_ROOM, INSERT_USER_ROOM, INSERT_USER, GET_ROOM_PREFERENCE,
        GET_ROOMS_FOR_USER, GET_DEVICES_FOR_ROOM, INSERT_DEVICE, INSERT_GATEWAY
    }
}
