package com.luma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

 data class ScannerPoint(val angle:Float,val distanceMm:Float)
 object ScannerProtocol { fun parse(line:String):ScannerPoint? { val p=line.trim().split(','); if(p.size!=2)return null; val a=p[0].toFloatOrNull()?:return null; val d=p[1].toFloatOrNull()?:return null; return if(d>0f) ScannerPoint(a,d) else null } }
 class BluetoothManager { fun disconnect(){} /* Device discovery/read loop belongs here, not in Compose. */ }
 class ScannerRepository { fun demo():List<ScannerPoint> = (0..180 step 3).map { a -> val obstacle=if(a in 35..65) 850f else if(a in 112..138) 1250f else 2200f; ScannerPoint(a.toFloat(), obstacle + ((a*17)%70)) } }

class MainActivity:ComponentActivity(){ override fun onCreate(b:Bundle?){super.onCreate(b);setContent{LumaApp()}} }
@Composable fun LumaApp(){ var points by remember{mutableStateOf(emptyList<ScannerPoint>())}; var scanning by remember{mutableStateOf(false)}; val repo=remember{ScannerRepository()}; MaterialTheme(colorScheme=lightColorScheme(primary=Color(0xFF4255D4))){Scaffold(bottomBar={NavigationBar{listOf("HOME","SEARCH","CREATE","MESSAGES","PROFILE").forEach{NavigationBarItem(selected=it=="HOME",onClick={},icon={},label={Text(it)})}}}){pad->Column(Modifier.padding(pad).padding(20.dp)){Text("LUMA",style=MaterialTheme.typography.headlineLarge);Text("Mini LiDAR",style=MaterialTheme.typography.titleMedium);Text(if (scanning) "Simulation Mode • scanning" else "Ready for offline demo",color=Color.Gray);Spacer(Modifier.height(16.dp));Radar(points);Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){Button(onClick={points=repo.demo();scanning=true}){Text("START SCAN")};OutlinedButton(onClick={scanning=false}){Text("STOP")};OutlinedButton(onClick={points=emptyList()}){Text("CLEAR")}};Spacer(Modifier.height(12.dp));Text("Points: ${points.size}   Range: 0–4000 mm",color=Color.Gray)}}}}
@Composable fun Radar(points:List<ScannerPoint>){Canvas(Modifier.fillMaxWidth().height(380.dp)){val c=Offset(size.width/2,size.height/2);val r=minOf(size.width,size.height)*.42f; for(i in 1..4)drawCircle(Color(0xFFE1E4EE),r*i/4,center=c,style=Stroke(1f));drawLine(Color(0xFF4255D4),Offset(c.x-r,c.y),Offset(c.x+r,c.y),1f);drawLine(Color(0xFF4255D4),Offset(c.x,c.y-r),Offset(c.x,c.y+r),1f);drawCircle(Color(0xFF4255D4),10f,c);points.forEach{p->val d=(p.distanceMm/4000f)*r;val rad=Math.toRadians((p.angle-90).toDouble());drawCircle(Color(0xFFEF6C5B),4f,Offset(c.x+(cos(rad)*d).toFloat(),c.y+(sin(rad)*d).toFloat()))}}}
