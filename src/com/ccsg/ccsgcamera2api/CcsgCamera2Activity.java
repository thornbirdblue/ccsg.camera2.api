package com.ccsg.ccsgcamera2api;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;

public class CcsgCamera2Activity extends Activity {
	private final String TAG = "CcsgCamera2";
	private CameraManager CM;
	private CDStateCallback CDStateCB;
	private CameraDevice	mCameraDevice;
	private Handler			mDeviceHandler,mSessionHandler;
	private SurfaceTexture	mSurfaceTexture;
	private CSStateCallback CSStateCB;
	private CameraCaptureSession mCameraCaptureSession;
	private CSCaptureCallback CSCaptureCB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CM = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
		CDStateCB = new CDStateCallback();
		mDeviceHandler = new Handler(){
			public void handleMessage(android.os.Message msg)
			{
				Log.d(TAG,"mDeviceHandler handleMessage");
			}
		};
		mSessionHandler = new Handler(){
			public void handleMessage(android.os.Message msg)
			{
				Log.d(TAG,"mSessionHandler handleMessage");
			}
		};
		try {
			Log.d(TAG,"openCamera begin!!!");
			CM.openCamera("0", CDStateCB, mDeviceHandler);
			Log.d(TAG,"openCamera end!!!");
		} catch (CameraAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mSurfaceTexture = new SurfaceTexture(10);
		CSStateCB = new CSStateCallback();
		setContentView(R.layout.activity_ccsg_camera2);
	}
	
	public class CDStateCallback extends CameraDevice.StateCallback{
		public void onOpened(CameraDevice camera)
		{
			Log.d(TAG,"onOpened");
			mCameraDevice = camera;
			Log.d(TAG,"new Surface begin!!!");
			Surface surface = new Surface(mSurfaceTexture);
			Log.d(TAG,"new Surface end!!!");
			try {
				Log.d(TAG,"createCaptureSession begin!!!");
				mCameraDevice.createCaptureSession(Arrays.asList(surface),CSStateCB,mSessionHandler);
				Log.d(TAG,"createCaptureSession end!!!");
			} catch (CameraAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void onClosed(CameraDevice camera)
		{
			Log.d(TAG,"onClosed");
		}
		public void onDisconnected(CameraDevice camera)
		{
			Log.d(TAG,"onDisconnected");
		}
		public void onError(CameraDevice camera, int error)
		{
			Log.d(TAG,"onError"+"error val is"+error);
		}
	}
	public class CSStateCallback extends CameraCaptureSession.StateCallback
	{
		public void onConfigured(CameraCaptureSession session)
		{			
			Log.d(TAG,"onConfigured");
			mCameraCaptureSession = session;
			
			CaptureRequest.Builder builder;
			
			try {
				builder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
			} catch (CameraAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void onConfigureFailed(CameraCaptureSession session)
		{
			Log.d(TAG,"onConfigureFailed");
		}		
		public void onReady(CameraCaptureSession session)
		{
			Log.d(TAG,"onReady");
		}
		public void onActive(CameraCaptureSession session)
		{
			Log.d(TAG,"onActive");
		}
		public void onClosed(CameraCaptureSession session)
		{
			Log.d(TAG,"onClosed");
		}
	}
	public class CSCaptureCallback extends CaptureCallback {
		public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber)
		{
			Log.d(TAG,"onCaptureStarted");
		}
		public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp)
		{
			Log.d(TAG,"onCaptureStarted");
		}
		public void onCapturePartial(CameraCaptureSession session, CaptureRequest request, CaptureResult result)
		{
			Log.d(TAG,"onCapturePartial");
		}
		public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult)
		{
			Log.d(TAG,"onCaptureProgressed");
		}
		public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result)
		{
			Log.d(TAG,"onCaptureCompleted");
		}
		public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure)
		{
			Log.d(TAG,"onCaptureFailed");
		}
		public void onCaptureSequenceCompleted(CameraCaptureSession session, int sequenceId, long frameNumber)
		{
			Log.d(TAG,"onCaptureSequenceCompleted");
		}
		public void onCaptureSequenceAborted(CameraCaptureSession session, int sequenceId)
		{
			Log.d(TAG,"onCaptureSequenceAborted");
		}
	}
}
