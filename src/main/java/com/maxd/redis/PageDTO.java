package com.maxd.redis;

import java.util.List;


public class PageDTO<T> {
    private int total;
    private int size = 10;
    private int pages;
    private int current = 1;
    private List<T> records;
    private int startIndex;
    private int endIndex;

    public PageDTO(int current, int size, int total) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.setRangeParameter(current, size);
        this.getTotalPages();
    }

    public void getTotalPages() {
        if (this.current % this.size == 0) {
            this.pages = this.total / this.size;
        } else {
            this.pages = this.total / this.size + 1;
        }

    }

    public void setRangeParameter(int current, int size) {
        this.startIndex = (current - 1) * size;
        this.endIndex = this.startIndex + size - 1;
    }

    public int getTotal() {
        return this.total;
    }

    public int getSize() {
        return this.size;
    }

    public int getPages() {
        return this.pages;
    }

    public int getCurrent() {
        return this.current;
    }

    public List<T> getRecords() {
        return this.records;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageDTO)) {
            return false;
        } else {
            PageDTO<?> other = (PageDTO) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getTotal() != other.getTotal()) {
                return false;
            } else if (this.getSize() != other.getSize()) {
                return false;
            } else if (this.getPages() != other.getPages()) {
                return false;
            } else if (this.getCurrent() != other.getCurrent()) {
                return false;
            } else {
                Object this$records = this.getRecords();
                Object other$records = other.getRecords();
                if (this$records == null) {
                    if (other$records != null) {
                        return false;
                    }
                } else if (!this$records.equals(other$records)) {
                    return false;
                }

                if (this.getStartIndex() != other.getStartIndex()) {
                    return false;
                } else if (this.getEndIndex() != other.getEndIndex()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageDTO;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getTotal();
        result = result * 59 + this.getSize();
        result = result * 59 + this.getPages();
        result = result * 59 + this.getCurrent();
        Object $records = this.getRecords();
        result = result * 59 + ($records == null ? 43 : $records.hashCode());
        result = result * 59 + this.getStartIndex();
        result = result * 59 + this.getEndIndex();
        return result;
    }

    public String toString() {
        return "PageDTO(total=" + this.getTotal() + ", size=" + this.getSize() + ", pages=" + this.getPages() + ", current=" + this.getCurrent() + ", records=" + this.getRecords() + ", startIndex=" + this.getStartIndex() + ", endIndex=" + this.getEndIndex() + ")";
    }
}
