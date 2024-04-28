import {Context} from '../../core/context';

export declare class Bounds {
    static EMPTY: Bounds;
    readonly left: number;
    readonly top: number;
    readonly width: number;
    readonly height: number;

    constructor(left: number, top: number, width: number, height: number);

    static fromClientRect(context: Context, clientRect: ClientRect): Bounds;

    static fromDOMRectList(context: Context, domRectList: DOMRectList): Bounds;

    add(x: number, y: number, w: number, h: number): Bounds;
}

export declare const parseBounds: (context: Context, node: Element) => Bounds;
export declare const parseDocumentSize: (document: Document) => Bounds;
