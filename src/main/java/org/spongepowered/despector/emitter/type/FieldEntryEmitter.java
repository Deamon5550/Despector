/*
 * The MIT License (MIT)
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.despector.emitter.type;

import org.spongepowered.despector.ast.Annotation;
import org.spongepowered.despector.ast.members.FieldEntry;
import org.spongepowered.despector.emitter.AstEmitter;
import org.spongepowered.despector.emitter.EmitterContext;
import org.spongepowered.despector.emitter.special.GenericsEmitter;

public class FieldEntryEmitter implements AstEmitter<FieldEntry> {

    @Override
    public boolean emit(EmitterContext ctx, FieldEntry ast) {
        for (Annotation anno : ast.getAnnotations()) {
            ctx.emit(anno);
            ctx.newLine();
            ctx.printIndentation();
        }
        ctx.printString(ast.getAccessModifier().asString());
        if (!ast.getAccessModifier().asString().isEmpty()) {
            ctx.printString(" ");
        }
        if (ast.isStatic()) {
            ctx.printString("static ");
        }
        if (ast.isFinal()) {
            ctx.printString("final ");
        }
        GenericsEmitter generics = ctx.getEmitterSet().getSpecialEmitter(GenericsEmitter.class);
        generics.emitTypeSignature(ctx, ast.getType());

        ctx.printString(" ");
        ctx.printString(ast.getName());
        if (ast.getInitializer() != null) {
            ctx.printString(" = ");
            ctx.emit(ast.getInitializer(), ast.getType());
        }
        return true;
    }

    private int getTypeLength(EmitterContext ctx, FieldEntry field) {
        int length = 0;
        length += field.getAccessModifier().asString().length();
        if (length > 0) {
            length++;
        }
        if (field.isStatic()) {
            length += 7;
        }
        if (field.isFinal()) {
            length += 6;
        }
        length += GenericsEmitter.getLength(ctx, field.getType());
        length++;
        return length;
    }

}
