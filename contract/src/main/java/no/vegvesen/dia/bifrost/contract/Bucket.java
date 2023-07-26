package no.vegvesen.dia.bifrost.contract;

public class Bucket {
    private final String name;
    private final String creationDate;
    private final int itemCountOnFirstPage;

    Bucket(final String name, final String creationDate, final int itemCountOnFirstPage) {
        this.name = name;
        this.creationDate = creationDate;
        this.itemCountOnFirstPage = itemCountOnFirstPage;
    }

    public static BucketBuilder builder() {
        return new BucketBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public int getItemCountOnFirstPage() {
        return this.itemCountOnFirstPage;
    }

    public static class BucketBuilder {
        private String name;
        private String creationDate;
        private int itemCountOnFirstPage;

        BucketBuilder() {
        }

        public BucketBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public BucketBuilder creationDate(final String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public BucketBuilder itemCountOnFirstPage(final int itemCountOnFirstPage) {
            this.itemCountOnFirstPage = itemCountOnFirstPage;
            return this;
        }

        public Bucket build() {
            return new Bucket(this.name, this.creationDate, this.itemCountOnFirstPage);
        }

        public String toString() {
            return "Bucket.BucketBuilder(name=" + this.name + ", creationDate=" + this.creationDate + ", itemCountOnFirstPage=" + this.itemCountOnFirstPage + ")";
        }
    }
}
