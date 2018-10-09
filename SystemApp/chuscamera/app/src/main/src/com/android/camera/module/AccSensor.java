package com.android.camera.module;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chus.camera.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by THINK on 2017/7/6.
 */

public class AccSensor implements SensorEventListener {

    //static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_FASTEST;
    static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_GAME;
    //static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_UI;
    //static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;

    Context mContext;
    ViewGroup mRootView;
    SensorManager mSensorManager;
    private int[] mGData = new int[3];

    LineChartView mChartView = null;
    private int maxNumberOfLines = 3;
    private int numberOfLines = maxNumberOfLines;
    private int maxNumberOfPoints = 256;
    private int numberOfPoints = 0;
//    float[][] randomNumbersTab = new float[maxNumberOfLines][maxNumberOfPoints];
    LinkedList[] allDatas = new LinkedList[3];

    private boolean hasAxes = true;
    private boolean hasAxesNames = false; // true;
    private boolean hasLines = true;
    private boolean hasPoints = false; // true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;

    private LineChartData mChartData;

    private TextView mInfo_1 = null;
    public AccSensor(Context context, ViewGroup rootView) {
        mContext = context;
        mRootView = rootView;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        allDatas[0] = new LinkedList<Integer>();
        allDatas[1] = new LinkedList<Integer>();
        allDatas[2] = new LinkedList<Integer>();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.sensor_debug_chart_1, null, false);
        mChartView = (LineChartView)viewGroup.findViewById(R.id.chart);
        mChartView.setInteractive(false);
        mChartView.setFocusable(false);
        mChartView.setViewportCalculationEnabled(false);
        resetViewport();

        mInfo_1 = (TextView)viewGroup.findViewById(R.id.info_1);
        mInfo_1.setText("Hello,world!");

		viewGroup.setVisibility(View.INVISIBLE); // frankie, hide

        ViewGroup debugViewContainer = (ViewGroup)mRootView.findViewById(R.id.sensor_debug_overlay_1);
        debugViewContainer.addView(viewGroup);
    }
    public void onResume() {
        Sensor gsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (gsensor != null) {
            mSensorManager.registerListener(this, gsensor, SENSOR_DELAY);
        }
    }
    public void onPause() {
        Sensor gsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (gsensor != null) {
            mSensorManager.unregisterListener(this, gsensor);
        }
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(mChartView.getMaximumViewport());
        v.bottom = -1500;
        v.top = 1500;
        v.left = 0;
        v.right = 300; // numberOfPoints - 1;
        mChartView.setMaximumViewport(v);
        mChartView.setCurrentViewport(v);
    }
    private void putDataOnView() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; i++) {

            List<PointValue> values = new ArrayList<PointValue>();

            Iterator<Integer> iterator = allDatas[i].iterator();
            for (int j = 0; j < numberOfPoints; j++) {
                if(iterator.hasNext()) {
                    values.add(new PointValue(j, (int) iterator.next()));
                }
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        mChartData = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            mChartData.setAxisXBottom(axisX);
            mChartData.setAxisYLeft(axisY);
        } else {
            mChartData.setAxisXBottom(null);
            mChartData.setAxisYLeft(null);
        }

        mChartData.setBaseValue(Float.NEGATIVE_INFINITY);

        mChartView.setLineChartData(mChartData);
    }
//    private void generateValues() {
//        for (int i = 0; i < maxNumberOfLines; ++i) {
//            for (int j = 0; j < maxNumberOfPoints; ++j) {
//                randomNumbersTab[i][j] = (float) Math.random() * 100f;
//            }
//        }
//    }
    private void putValues(int[] data) {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            if(allDatas[i].size() >= maxNumberOfPoints) {
                allDatas[i].removeFirst();
                numberOfPoints--;
            }
            allDatas[i].add(data[i]);
            numberOfPoints++;
        }
    }


    static final int AVERAGE_SIZE = 20;
    private int[] average_write_index1 = new int[3];
    private int[] average_write_index2 = new int[3];
    private float[][] averageFilterBuffer = new float[3][AVERAGE_SIZE];

    private float __average(float[] arr_, int length) {
        float r = 0.0f;
        for(int i=0;i<length;i++) {
            r += arr_[i];
        }
        return r/length;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        for (int i = 0; i < 3 ; i++) {
            if(average_write_index2[i] >= AVERAGE_SIZE) {
                average_write_index2[i] = 0;
            }
            averageFilterBuffer[i][average_write_index2[i]] = event.values[i];
            average_write_index2[i]++;
            if(average_write_index1[i] < AVERAGE_SIZE) {
                average_write_index1[i]++;
            }
        }

        for (int i = 0; i < 3 ; i++) {
            if(average_write_index1[i] >= AVERAGE_SIZE) {
                float __value = __average(averageFilterBuffer[i], AVERAGE_SIZE)*100;
                mGData[i] = (int) __value;
            }
        }

        if(mInfo_1 != null) {
            double degree = 90.0f;
            double xy_vector = mGData[0]*mGData[0] + mGData[1]*mGData[1];
            xy_vector = (double) Math.sqrt(xy_vector);
            if(xy_vector > 0.01f) {
                double gravity_ = (float) Math.sqrt(xy_vector*xy_vector + mGData[2]*mGData[2]);
                double sin = xy_vector/gravity_;
                double asin = Math.asin(sin);
                double degree_ = (asin*180)/Math.PI;
                mInfo_1.setText("degree_:" + degree_);
            }
        }

        putValues(mGData);
        putDataOnView();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
