package com.l1.tp_2.views.login_historic;

import static com.l1.tp_2.views.password_login.PasswordLoginActivity.LOGIN_HISTORY_PREFERENCES;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.entities.LoginHistoric;

import java.util.List;

public class LoginHistoricActivity extends AppCompatActivity implements LoginHistoricContract.View {

    private LoginHistoricContract.Presenter presenter;
    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_historic);
        presenter = new LoginHistoricPresenter(getSharedPreferences(LOGIN_HISTORY_PREFERENCES, Context.MODE_PRIVATE));

        List<LoginHistoric> loginHistoric = presenter.onLoadData();

        mTableLayout = (TableLayout) findViewById(R.id.tableInvoices);
        mTableLayout.setStretchAllColumns(true);
        createTable(loginHistoric);
    }

    public void createTable(List<LoginHistoric> loginHistoric) {
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;
        int textSize = 44, smallTextSize = 40;
        int rows = loginHistoric.size();
        TextView textSpacer;
        mTableLayout.removeAllViews();
        // -1 means heading row
        for (int i = -1; i < rows; i++) {
            LoginHistoric row = null;
            if (i > -1) {
                row = loginHistoric.get(i);
            } else {
                textSpacer = new TextView(this);
                textSpacer.setText("");
            }
            // data columns
            final TextView userColumn = new TextView(this);
            userColumn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            userColumn.setGravity(Gravity.START);
            userColumn.setPadding(5, 15, 0, 15);
            if (i == -1) {
                userColumn.setText("Usuario");
                userColumn.setBackgroundColor(Color.parseColor("#f0f0f0"));
                userColumn.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            } else {
                userColumn.setBackgroundColor(Color.parseColor("#f8f8f8"));
                userColumn.setText(String.valueOf(row.getFullName()));
                userColumn.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            final LinearLayout dateColumn = new LinearLayout(this);
            dateColumn.setOrientation(LinearLayout.VERTICAL);
            dateColumn.setPadding(0, 10, 0, 10);
            dateColumn.setBackgroundColor(Color.parseColor("#f8f8f8"));
            final TextView lastAccess = new TextView(this);
            if (i == -1) {
                lastAccess.setText("Último acceso");
                lastAccess.setBackgroundColor(Color.parseColor("#f0f0f0"));
            } else {
                lastAccess.setBackgroundColor(Color.parseColor("#f8f8f8"));
                lastAccess.setTextColor(Color.parseColor("#000000"));
                lastAccess.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                lastAccess.setText((String) row.getDate());
            }
            dateColumn.addView(lastAccess);
            // add table row
            final TableRow tableRow = new TableRow(this);
            tableRow.setId(i + 1);
            TableLayout.LayoutParams trParams = new
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin,
                    bottomRowMargin);
            tableRow.setPadding(0, 0, 0, 0);
            tableRow.setLayoutParams(trParams);
            tableRow.addView(userColumn);
            tableRow.addView(dateColumn);
            mTableLayout.addView(tableRow, trParams);
            if (i > -1) {
                // add separator row
                final TableRow trSep = new TableRow(this);
                TableLayout.LayoutParams trParamsSep = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(leftRowMargin, topRowMargin,
                        rightRowMargin, bottomRowMargin);
                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 2;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }
    }

}