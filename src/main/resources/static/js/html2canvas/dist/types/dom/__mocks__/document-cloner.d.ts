export declare class DocumentCloner {
    clonedReferenceElement?: HTMLElement;

    constructor();

    static destroy(): boolean;

    toIFrame(): Promise<HTMLIFrameElement>;
}
