package com.eastsoft.android.esbic.ativity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.service.IModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectSetActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemClickListener
{
    private EditText deviceAddress1, deviceAddress2, deviceAddress3, deviceAddress4;
    private EditText ip1, ip2, ip3, ip4;
    private EditText subnetMask1, subnetMask2, subnetMask3, subnetMask4;
    private EditText gateway1, gateway2, gateway3, gateway4;
    private EditText impServerAddr1, impServerAddr2, impServerAddr3, impServerAddr4;
    private EditText centerManagementAddr1, centerManagementAddr2, centerManagementAddr3, centerManagementAddr4;
    private EditText currentEditText;
    private List<EditText> editTexts = new ArrayList<>();
    private TextView back2;
    private ImageButton back;
    private GridView inputKeyBoard;
    private int[] icon;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_setting);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        back = (ImageButton) this.findViewById(R.id.project_setting_back);
        back.setOnClickListener(this);
        back2 = (TextView) this.findViewById(R.id.project_setting_back2);
        back2.setOnClickListener(this);
        inputKeyBoard = (GridView) this.findViewById(R.id.project_setting_input_keyboard);
        icon = new int[]{R.drawable.num_delete, R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter = new InputKeyBoardAdapter(this, icon, "确认");
        inputKeyBoard.setAdapter(inputKeyBoardAdapter);
        inputKeyBoard.setOnItemClickListener(this);

        deviceAddress1 = (EditText) this.findViewById(R.id.setting_device_first);
        deviceAddress2 = (EditText) this.findViewById(R.id.setting_device_second);
        deviceAddress3 = (EditText) this.findViewById(R.id.setting_device_third);
        deviceAddress4 = (EditText) this.findViewById(R.id.setting_device_fourth);
        ip1 = (EditText) this.findViewById(R.id.setting_ip_first);
        ip2 = (EditText) this.findViewById(R.id.setting_ip_second);
        ip3 = (EditText) this.findViewById(R.id.setting_ip_third);
        ip4 = (EditText) this.findViewById(R.id.setting_ip_fourth);
        subnetMask1 = (EditText) this.findViewById(R.id.setting_submask_first);
        subnetMask2 = (EditText) this.findViewById(R.id.setting_submask_second);
        subnetMask3 = (EditText) this.findViewById(R.id.setting_submask_third);
        subnetMask4 = (EditText) this.findViewById(R.id.setting_submask_fourth);
        gateway1 = (EditText) this.findViewById(R.id.setting_gateway_first);
        gateway2 = (EditText) this.findViewById(R.id.setting_gateway_second);
        gateway3 = (EditText) this.findViewById(R.id.setting_gateway_third);
        gateway4 = (EditText) this.findViewById(R.id.setting_gateway_fourth);
        impServerAddr1 = (EditText) this.findViewById(R.id.setting_imp_first);
        impServerAddr2 = (EditText) this.findViewById(R.id.setting_imp_second);
        impServerAddr3 = (EditText) this.findViewById(R.id.setting_imp_third);
        impServerAddr4 = (EditText) this.findViewById(R.id.setting_imp_fourth);
        centerManagementAddr1 = (EditText) this.findViewById(R.id.setting_center_first);
        centerManagementAddr2 = (EditText) this.findViewById(R.id.setting_center_second);
        centerManagementAddr3 = (EditText) this.findViewById(R.id.setting_center_third);
        centerManagementAddr4 = (EditText) this.findViewById(R.id.setting_center_fourth);

        deviceAddress1.setOnFocusChangeListener(this);
        deviceAddress2.setOnFocusChangeListener(this);
        deviceAddress3.setOnFocusChangeListener(this);
        deviceAddress4.setOnFocusChangeListener(this);
        ip1.setOnFocusChangeListener(this);
        ip2.setOnFocusChangeListener(this);
        ip3.setOnFocusChangeListener(this);
        ip4.setOnFocusChangeListener(this);
        subnetMask1.setOnFocusChangeListener(this);
        subnetMask2.setOnFocusChangeListener(this);
        subnetMask3.setOnFocusChangeListener(this);
        subnetMask4.setOnFocusChangeListener(this);
        gateway1.setOnFocusChangeListener(this);
        gateway2.setOnFocusChangeListener(this);
        gateway3.setOnFocusChangeListener(this);
        gateway4.setOnFocusChangeListener(this);
        impServerAddr1.setOnFocusChangeListener(this);
        impServerAddr2.setOnFocusChangeListener(this);
        impServerAddr3.setOnFocusChangeListener(this);
        impServerAddr4.setOnFocusChangeListener(this);
        centerManagementAddr1.setOnFocusChangeListener(this);
        centerManagementAddr2.setOnFocusChangeListener(this);
        centerManagementAddr3.setOnFocusChangeListener(this);
        centerManagementAddr4.setOnFocusChangeListener(this);

        editTexts.add(deviceAddress1);
        editTexts.add(deviceAddress2);
        editTexts.add(deviceAddress3);
        editTexts.add(deviceAddress4);
        editTexts.add(ip1);
        editTexts.add(ip2);
        editTexts.add(ip3);
        editTexts.add(ip4);
        editTexts.add(subnetMask1);
        editTexts.add(subnetMask2);
        editTexts.add(subnetMask3);
        editTexts.add(subnetMask4);
        editTexts.add(gateway1);
        editTexts.add(gateway2);
        editTexts.add(gateway3);
        editTexts.add(gateway4);
        editTexts.add(impServerAddr1);
        editTexts.add(impServerAddr2);
        editTexts.add(impServerAddr3);
        editTexts.add(impServerAddr4);
        editTexts.add(centerManagementAddr1);
        editTexts.add(centerManagementAddr2);
        editTexts.add(centerManagementAddr3);
        editTexts.add(centerManagementAddr4);

        currentEditText = deviceAddress1;

        IModelService modelService = ((MyApplication) getApplication()).getModelService();
        DeviceInfo deviceInfo = modelService.getDeviceInfo();
        if (deviceInfo != null)
        {
            deviceAddress1.setText(String.format("%02d", deviceInfo.getBuilding_no()));
            deviceAddress2.setText(String.format("%02d", deviceInfo.getUnit_no()));
            deviceAddress3.setText(String.format("%02d", deviceInfo.getLayer_no()));
            deviceAddress4.setText(String.format("%02d", deviceInfo.getRoom_no()));

            currentEditText = deviceAddress4;
            currentEditText.setFocusable(true);
            currentEditText.requestFocus();
            Editable ea = deviceAddress4.getText();
            deviceAddress4.setSelection(ea.length());
        }

        IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
        if (ipAddressInfo != null)
        {
            String[] tmp;
            if (ipAddressInfo.getIp().compareTo("") != 0)
            {
                tmp = ipAddressInfo.getIp().split("\\.");
                ip1.setText(tmp[0]);
                ip2.setText(tmp[1]);
                ip3.setText(tmp[2]);
                ip4.setText(tmp[3]);
            }
            if (ipAddressInfo.getSubnetMask().compareTo("") != 0)
            {
                tmp = ipAddressInfo.getSubnetMask().split("\\.");
                subnetMask1.setText(tmp[0]);
                subnetMask2.setText(tmp[1]);
                subnetMask3.setText(tmp[2]);
                subnetMask4.setText(tmp[3]);
            }
            if (ipAddressInfo.getGateway().compareTo("") != 0)
            {
                tmp = ipAddressInfo.getGateway().split("\\.");
                gateway1.setText(tmp[0]);
                gateway2.setText(tmp[1]);
                gateway3.setText(tmp[2]);
                gateway4.setText(tmp[3]);
            }
            if (ipAddressInfo.getImpAdress().compareTo("") != 0)
            {
                tmp = ipAddressInfo.getImpAdress().split("\\.");
                impServerAddr1.setText(tmp[0]);
                impServerAddr2.setText(tmp[1]);
                impServerAddr3.setText(tmp[2]);
                impServerAddr4.setText(tmp[3]);
            }
            if (ipAddressInfo.getCenterAddress().compareTo("") != 0)
            {
                tmp = ipAddressInfo.getCenterAddress().split("\\.");
                centerManagementAddr1.setText(tmp[0]);
                centerManagementAddr2.setText(tmp[1]);
                centerManagementAddr3.setText(tmp[2]);
                centerManagementAddr4.setText(tmp[3]);
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        playMusic();
        if (view.getId() == back.getId() || view.getId() == back2.getId())
        {
            finish();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        if (view.getId() == R.id.setting_device_first)
        {
            currentEditText = deviceAddress1;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_device_second)
        {
            currentEditText = deviceAddress2;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_device_third)
        {
            currentEditText = deviceAddress3;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_device_fourth)
        {
            currentEditText = deviceAddress4;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_ip_first)
        {
            currentEditText = ip1;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_ip_second)
        {
            currentEditText = ip2;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_ip_third)
        {
            currentEditText = ip3;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_ip_fourth)
        {
            currentEditText = ip4;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_submask_first)
        {
            currentEditText = subnetMask1;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_submask_second)
        {
            currentEditText = subnetMask2;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_submask_third)
        {
            currentEditText = subnetMask3;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_submask_fourth)
        {
            currentEditText = subnetMask4;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_gateway_first)
        {
            currentEditText = gateway1;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_gateway_second)
        {
            currentEditText = gateway2;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_gateway_third)
        {
            currentEditText = gateway3;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_gateway_fourth)
        {
            currentEditText = gateway4;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_imp_first)
        {
            currentEditText = impServerAddr1;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_imp_second)
        {
            currentEditText = impServerAddr2;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_imp_third)
        {
            currentEditText = impServerAddr3;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_imp_fourth)
        {
            currentEditText = impServerAddr4;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_center_first)
        {
            currentEditText = centerManagementAddr1;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_center_second)
        {
            currentEditText = centerManagementAddr2;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_center_third)
        {
            currentEditText = centerManagementAddr3;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        } else if (view.getId() == R.id.setting_center_fourth)
        {
            currentEditText = centerManagementAddr4;
            currentEditText.setSelection(currentEditText.getText().toString().length());
        }
        imm.hideSoftInputFromWindow(currentEditText.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        playMusic();
        textHowToShow(i);
    }

    public void textHowToShow(int position)
    {
        if (position < 9 || position == 10)
        {
            String tmp = (position == 10 ? "0" : (String.valueOf(position + 1)));
            append(tmp);
        } else if (position == 9)
        {
            saveData();
        } else if (position == 11)
        {
            delete();
        }
    }

    private void append(String tmp)
    {
        int len = currentEditText.getText().toString().trim().length();
        if(currentEditText == deviceAddress1 || currentEditText == deviceAddress2 || currentEditText == deviceAddress3 || currentEditText == deviceAddress4)
        {
            if(len == 0)
            {
                currentEditText.append(tmp);
            }else if(len == 1)
            {
                currentEditText.append(tmp);
                int index = editTexts.indexOf(currentEditText);
                if(currentEditText != centerManagementAddr4)
                {
                    currentEditText = editTexts.get(index+1);
                    currentEditText.setFocusable(true);
                    currentEditText.requestFocus();
                }
            }
        }else
        {
            if(len < 2)
            {
                currentEditText.append(tmp);
            }else if(len == 2)
            {
                currentEditText.append(tmp);
                int index = editTexts.indexOf(currentEditText);
                if(currentEditText != centerManagementAddr4)
                {
                    currentEditText = editTexts.get(index+1);
                    currentEditText.setFocusable(true);
                    currentEditText.requestFocus();
                }
            }else if(len == 3)
            {
                int index = editTexts.indexOf(currentEditText);
                if(currentEditText != centerManagementAddr4)
                {
                    currentEditText = editTexts.get(index+1);
                    currentEditText.setFocusable(true);
                    currentEditText.requestFocus();
                    currentEditText.append(tmp);
                }else
                {
                    currentEditText.setSelection(currentEditText.getText().toString().length());
                }
            }
        }
    }

    private void delete()
    {
        Editable edit = currentEditText.getEditableText();
        int len = edit.length();
        if (len != 0)
        {
            int index = currentEditText.getSelectionStart();
            String str = currentEditText.getText().toString();
            if(index == 0)
            {
                int ind = editTexts.indexOf(currentEditText);
                if(currentEditText != deviceAddress1)
                {
                    currentEditText = editTexts.get(ind-1);
                    currentEditText.setFocusable(true);
                    currentEditText.requestFocus();
                    str = currentEditText.getText().toString();
                    currentEditText.setSelection(str.length());
                    int location = currentEditText.getSelectionStart();
                    if (!str.equals(""))
                    {
                        currentEditText.getText().delete(location - 1, location);
                    }
                }
            }else if (!str.equals(""))
            {
                currentEditText.getText().delete(index - 1, index);
            }
        }else
        {
            int index = editTexts.indexOf(currentEditText);
            if(currentEditText != deviceAddress1)
            {
                currentEditText = editTexts.get(index-1);
                currentEditText.setFocusable(true);
                currentEditText.requestFocus();
                String str = currentEditText.getText().toString();
                currentEditText.setSelection(str.length());
                int location = currentEditText.getSelectionStart();
                if (!str.equals(""))
                {
                    currentEditText.getText().delete(location - 1, location);
                }
            }
        }
    }

    private void saveData()
    {
        String addr1 = deviceAddress1.getText().toString().trim();
        String addr2 = deviceAddress2.getText().toString().trim();
        String addr3 = deviceAddress3.getText().toString().trim();
        String addr4 = deviceAddress4.getText().toString().trim();
        if (addr1.length() != 2 || addr2.length() != 2 || addr3.length() != 2 || addr4.toString().length() != 2)
        {
            showLongToast("设备地址错误，请重新设置！");
            return;
        }
        DeviceInfo deviceInfo = new DeviceInfo(DeviceTypeEnum.DT_ROOM_MACHINE.getType(),
                Integer.parseInt(addr1),
                (byte) Integer.parseInt(addr2),
                (byte) Integer.parseInt(addr3),
                (byte) Integer.parseInt(addr4), 1);

        Pattern pattern = Pattern.compile("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)($|(?!\\.$)\\.)){4}$");
        Matcher matcher;
        String ipStr = ip1.getText().toString().trim() + "." + ip2.getText().toString().trim() + "." + ip3.getText().toString().trim() + "." + ip4.getText().toString().trim();
        matcher = pattern.matcher(ipStr);
        if (ipStr.length() != 0 && ipStr.length() != 15 || !matcher.matches())
        {
            showLongToast("IP地址错误，请重新设置！");
            return;
        }
        String subnetMaskStr = subnetMask1.getText().toString().trim() + "." + subnetMask2.getText().toString().trim() + "." + subnetMask3.getText().toString().trim() + "." + subnetMask4.getText().toString().trim();
        ;
        matcher = pattern.matcher(subnetMaskStr);
        if (subnetMaskStr.length() != 0 && subnetMaskStr.length() != 15 || !matcher.matches())
        {
            showLongToast("子网掩码错误，请重新设置！");
            return;
        }
        String gatewayStr = gateway1.getText().toString().trim() + "." + gateway2.getText().toString().trim() + "." + gateway3.getText().toString().trim() + "." + gateway4.getText().toString().trim();
        matcher = pattern.matcher(gatewayStr);
        if (gatewayStr.length() != 0 && gatewayStr.length() != 15 || !matcher.matches())
        {
            showLongToast("网关地址错误，请重新设置！");
            return;
        }
        String imp = impServerAddr1.getText().toString().trim() + "." + impServerAddr2.getText().toString().trim() + "." + impServerAddr3.getText().toString().trim() + "." + impServerAddr4.getText().toString().trim();
        matcher = pattern.matcher(imp);
        if (imp.length() != 0 && imp.length() != 15 || !matcher.matches())
        {
            showLongToast("服务器地址错误，请重新设置！");
            return;
        }
        String center = centerManagementAddr1.getText().toString().trim() + "." + centerManagementAddr2.getText().toString().trim() + "." + centerManagementAddr3.getText().toString().trim() + "." + centerManagementAddr4.getText().toString().trim();
        matcher = pattern.matcher(center);
        if (center.length() != 0 && center.length() != 15 || !matcher.matches())
        {
            showLongToast("中心管理机地址错误，请重新设置！");
            return;
        }
        IpAddressInfo ipAddressInfo = new IpAddressInfo(ipStr, subnetMaskStr, gatewayStr, imp, center);
        ((MyApplication) getApplication()).getModelService().setDeviceInfo(deviceInfo);
        ((MyApplication) getApplication()).getModelService().setIpAddressInfo(ipAddressInfo);
        showLongToast("设置成功！");
    }
}
