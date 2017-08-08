package io.zulia.ui.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Collections;
import java.util.List;

/**
 * Created by Payam Meyer on 3/10/17.
 * @author pmeyer
 */
public class InstanceInfo implements IsSerializable {

	private List<IndexInfo> indexes = Collections.emptyList();
	private String zuliaVersion;
	private String luceneVersion;
	private long zuliaMemory;
	private long diskSize;
	private long jvmUsedMemory;
	private long jvmFreeMemory;
	private long jvmTotalMemoryMB;
	private long jvmMaxMemoryMB;

	public List<IndexInfo> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<IndexInfo> indexes) {
		this.indexes = indexes;
	}

	public String getZuliaVersion() {
		return zuliaVersion;
	}

	public void setZuliaVersion(String zuliaVersion) {
		this.zuliaVersion = zuliaVersion;
	}

	public String getLuceneVersion() {
		return luceneVersion;
	}

	public void setLuceneVersion(String luceneVersion) {
		this.luceneVersion = luceneVersion;
	}

	public long getZuliaMemory() {
		return zuliaMemory;
	}

	public void setZuliaMemory(long zuliaMemory) {
		this.zuliaMemory = zuliaMemory;
	}

	public long getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(long diskSize) {
		this.diskSize = diskSize;
	}

	public void setJvmUsedMemory(long jvmUsedMemory) {
		this.jvmUsedMemory = jvmUsedMemory;
	}

	public long getJvmUsedMemory() {
		return jvmUsedMemory;
	}

	public void setJvmFreeMemory(long jvmFreeMemory) {
		this.jvmFreeMemory = jvmFreeMemory;
	}

	public long getJvmFreeMemory() {
		return jvmFreeMemory;
	}

	public void setJvmTotalMemoryMB(long jvmTotalMemoryMB) {
		this.jvmTotalMemoryMB = jvmTotalMemoryMB;
	}

	public long getJvmTotalMemoryMB() {
		return jvmTotalMemoryMB;
	}

	public void setJvmMaxMemoryMB(long jvmMaxMemoryMB) {
		this.jvmMaxMemoryMB = jvmMaxMemoryMB;
	}

	public long getJvmMaxMemoryMB() {
		return jvmMaxMemoryMB;
	}
}
