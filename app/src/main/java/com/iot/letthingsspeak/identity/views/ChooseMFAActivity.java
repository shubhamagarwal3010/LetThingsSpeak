/*
 * Copyright 2013-2017 Amazon.com,
 * Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the
 * License. A copy of the License is located at
 *
 *      http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, express or implied. See the License
 * for the specific language governing permissions and
 * limitations under the License.
 */

package com.iot.letthingsspeak.identity.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.AppHelper;

import java.util.List;

/**
 * Shows the available MFA options to the user.
 */
public class ChooseMFAActivity extends AppCompatActivity {

    private Button cancelButton;
    private ListView mfaOptionsList;
    private List<String> mfaOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mfa);

        init();
    }

    @Override
    public void onBackPressed() {
        exit(null);
    }

    private void init() {
        // Set cancel button and button click listener.
        cancelButton = findViewById(R.id.buttonChooseMfaCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        List<String> optionsList = AppHelper.getAllMfaOptions();

        // Set MFA options list.
        final ListView mfaOptionsListView = findViewById(R.id.listViewMfaOptions);
        final DisplayMfaOptionsAdapter listAdapter = new DisplayMfaOptionsAdapter(getApplicationContext());
        mfaOptionsListView.setAdapter(listAdapter);
        mfaOptionsList = mfaOptionsListView;
        mfaOptionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                exit(AppHelper.getMfaOptionCode(position));
            }
        });
    }

    // Return the selected MFA option.
    private void exit(String mfaOption) {
        Intent intent = new Intent();
        if (mfaOption == null)
            mfaOption = "";
        intent.putExtra("mfaOption", mfaOption);
        setResult(RESULT_OK, intent);
        finish();
    }
}
