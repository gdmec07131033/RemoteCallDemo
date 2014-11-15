package cn.edu.gdmec.t00385.remoteservicedemo;
import cn.edu.gdmec.t00385.remoteservicedemo.AllResult;
interface IMathService{
	long Add(long a,long b);
	AllResult  CompuerAny(long a,long b);
}