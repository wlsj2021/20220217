package com.wlsj2021.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.wlsj2021.myapplication.pay.AuthResult;
import com.wlsj2021.myapplication.pay.OrderInfoUtil2_0;
import com.wlsj2021.myapplication.pay.PayResult;

import java.util.Map;

/**
 *  重要说明：
 *  
 *  本 Demo 只是为了方便直接向商户展示支付宝的整个支付流程，所以将加签过程直接放在客户端完成
 *  （包括 OrderInfoUtil2_0_HK 和 OrderInfoUtil2_0）。
 *
 *  在真实 App 中，私钥（如 RSA_PRIVATE 等）数据严禁放在客户端，同时加签过程务必要放在服务端完成，
 *  否则可能造成商户私密数据泄露或被盗用，造成不必要的资金损失，面临各种安全风险。
 *
 *  Warning:
 *
 *  For demonstration purpose, the assembling and signing of the request parameters are done on
 *  the client side in this demo application.
 *
 *  However, in practice, both assembling and signing must be carried out on the server side.
 */
public class PayDemoActivity extends AppCompatActivity {
	
	/**
	 * 用于支付宝支付业务的入参 app_id。
	 */
	public static final String APPID =
			"2021000117683565";
	
	/**
	 * 用于支付宝账户登录授权业务的入参 pid。
	 */
	public static final String PID = "";

	/**
	 * 用于支付宝账户登录授权业务的入参 target_id。
	 */
	public static final String TARGET_ID = "";

	/**
	 *  pkcs8 格式的商户私钥。
	 *
	 * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
	 * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
	 * 	RSA2_PRIVATE。
	 *
	 * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
	 * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
	 */
	public static final String RSA2_PRIVATE ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDVpUH8vky69Qde6VbW8A4WH6qaN4VTWdLQ2s8CH+WD6QMeYdkpxDhOF8oHoA6WU2M4OO5B6QZiSWi62gtIIdfodwD/TFE9idk1PQkAx3RuG5FQf0039fFPMtBM/UzyrrwlCfeuoWB7g44fuYLkqLyrJM7d1/LTtB4DIidF8iB8IQDvjbkSf20grhVKvsnYMyA6IVwlVIzwx7y+wFuBFR1ftEUzXZPCJ/8n//M+KFzj1MN2hcsZdIpe3KnBmfMdeIHhUl5L9JAhBgvttNzIGLEr34J1heYJVMdpuO8+x3ArMT0Ysh75G4it8NjKNdbuEFqflXKpZ9f9CZLY8JO/hHeTAgMBAAECggEBAJMDQdISfShVPfowpOIIlBjjN3khZWxCrg+r0bDLlBreN5griszzyi7qrcnreL42qVrhygmuuQbfLuMM8aB0MWc0pIoI0QqAlefNlSllFphB/g+fWdp9Mczm0XF5B/8jADKYlkgjogm8MAtik1CXUCSQko0TcXP+SHytfsrh3zqqFWKgj3uYG9japaQZnEdneimSTU405FHw7NtpuUFQShMKLWaXeOMt+DPJJnXc62jEArAbrL1xzNRLq3+vyOs9dnfnChHxPxtrerfc829aX9tx4bcOHVY4TGWa6nhdIAMnoti1hwO3vuYVGoaiSwF4yXUmRLGWJeuiPfHbuM/qjuECgYEA7JC6nQ046D8Nucy1qB3T0wZrQTR0/J1Wn9N1eeIbaiYuAoAp4yPLrLpkv28GetBeVB14UAyrBB5ZIraIuZVDi9dhyOUW8U1u0kqLTafOaq0NAT6kBMIcHgzAFvVwa3Q6O641PqcZW/Oq4isTr4kH/2vYzoN65rkQaZj057/m68MCgYEA5zJ++DBOixj1qFsLGXIZOFcLvnFRWt6u3V9sExk2nTKKbi3F3d6B80nxivlaZ2whK0dIXY2qtzZsbLloMu+GhOsIf97RN3ZWRxydv0MuD6vbTXpMmsFYYRPFFDxsEiwmZl+O4ORtgV+IMH2rqssg38Zmgp9msUG/QvzALrMmF/ECgYEAniRmTAUBzOgR4epCad9+smxxGMbbieAYf4GZ39/SWdSqH0vUNJ7CUnbMUNkEqOdQl8SXoFPHo1oyVi1WG1H4NKzIFsdm3erYscXZeBE8wT1sGy/n77Jfn4sAgpiTDZJGN41O1NJZ9ole7BDF+SuO6CYNaoikiUaf/SeeWKqF0YcCgYBKBNqtrxmWYvlTW0KkdOkqJoQ2BqWbSCwH5+L8Alh/34x+Vd1BeVl1gLj6W3bcIbxE1N+QRCjh22S/MmTv2cvhhjYEalvlO9fHkJvYfjOjBMyDc6JO8ZPQELpKDkYzUztAD1v5GGkOwH0juThayAnQWv661znTik8nrTACQ4zwoQKBgQCqphdfi/upDi7A/i70GXcZaF3Qq6MoYApfY0vxARgyC3RBiCDTt9IKFXeynX8nyv7/pq0QFMgmLS1ELa8WMipGo7AK6YT74Mthz6J896/pGb92gfDGL9uYPoqYjvuS6erpIKzIHMqucgc73HH8vZXct3+aq0SnWVz/7jvoBTP7gg==";
	public static final String RSA_PRIVATE = "";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					showAlert(PayDemoActivity.this, getString(R.string.app_name) + payResult);
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					showAlert(PayDemoActivity.this, getString(R.string.fail) + payResult);
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
//					showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
				} else {
					// 其他状态值则为授权失败
//					showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		RxPermissions rxPermissions = new RxPermissions(this);

		rxPermissions
				.request(
						Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.INTERNET,
						Manifest.permission.ACCESS_NETWORK_STATE,
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.ACCESS_WIFI_STATE

				)
				.subscribe(granted -> {
					if (granted) {
						// All requested permissions are granted
					} else {
						// At least one permission is denied
					}
				});

	}

	/**
	 * 支付宝支付业务示例
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
			showAlert(this, getString(R.string.fail));
			return;
		}
	
		/*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo 的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		
		final Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付宝账户授权业务示例
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			showAlert(this, getString(R.string.fail));
			return;
		}

		/*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo 的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * 获取支付宝 SDK 版本号。
	 */
	public void showSdkVersion(View v) {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
//		showAlert(this, getString(R.string.alipay_sdk_version_is) + version);
	}

	/**
	 * 将 H5 网页版支付转换成支付宝 App 支付的示例
	 */
	public void h5Pay(View v) {
		WebView.setWebContentsDebuggingEnabled(true);
//		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();

		/*
		 * URL 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
		 *
		 * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
		 * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
		 * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
		 * 进行测试。
		 * 
		 * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
		 * 可以参考它实现自定义的 URL 拦截逻辑。
		 *
		 * 注意：WebView 的 shouldOverrideUrlLoading(url) 无法拦截直接调用 open(url) 打开的第一个 url，
		 * 所以直接设置 url = "https://mclient.alipay.com/cashier/mobilepay.htm......" 是无法完成网页转 Native 的。
		 * 如果需要拦截直接打开的支付宝网页支付 URL，可改为使用 shouldInterceptRequest(view, request) 。
		 */
		String url = "https://m.taobao.com";
//		extras.putString("url", url);
//		intent.putExtras(extras);
//		startActivity(intent);
	}

	private static void showAlert(Context ctx, String info) {
		showAlert(ctx, info, null);
	}

	private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
		new AlertDialog.Builder(ctx)
				.setMessage(info)
				.setPositiveButton(R.string.success, null)
				.setOnDismissListener(onDismiss)
				.show();
	}

	private static void showToast(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	}

	private static String bundleToString(Bundle bundle) {
		if (bundle == null) {
			return "null";
		}
		final StringBuilder sb = new StringBuilder();
		for (String key: bundle.keySet()) {
			sb.append(key).append("=>").append(bundle.get(key)).append("\n");
		}
		return sb.toString();
	}
}
