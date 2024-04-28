export interface FontMetric {
    baseline: number;
    middle: number;
}

export declare class FontMetrics {
    private readonly _data;
    private readonly _document;
    private parseMetrics;

    constructor(document: Document);

    getMetrics(fontFamily: string, fontSize: string): FontMetric;
}
