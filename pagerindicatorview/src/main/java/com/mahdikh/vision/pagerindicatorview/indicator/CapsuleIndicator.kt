package com.mahdikh.vision.pagerindicatorview.indicator

open class CapsuleIndicator : PathIndicator() {
    override fun adjustPath() {
        path.reset()
        val size = size()
        // عرض کپسول
        val width: Float = size / 2f

        //ارتفاع نیم دایره
        val semicircularHeight: Float = size * 0.25f

        //نصف ارتفاع نیم دایره
        val semicircularHalfHeight = semicircularHeight / 2f

        // نقطه سمت چپ متصل به خط عمودی محور X
        val pointLeftLineX: Float = baseInfo.x + (size - width) / 2

        // نقطه پایین متصل به خط عمودی محور Y
        val pointBottomLineY: Float = bottom(baseInfo) - semicircularHeight

        // نقطه بالا متصل به خط عمودی محور Y
        val pointTopLineY: Float = baseInfo.y + semicircularHeight

        // A نقطه سمت چپ پایین
        path.moveTo(pointLeftLineX, pointBottomLineY)
        // B نقطه سمت چپ بالا
        path.lineTo(pointLeftLineX, pointTopLineY)

        // کشیدن محور x مربوط به یک نقطه به سمت چپ
        val cubicXToLeft = pointLeftLineX + width / 4f

        // کشیدن محور y مربوط به یک نقطه به سمت بالا
        val cubicYToTop = pointTopLineY - semicircularHalfHeight

        // مرکز کپسول در محور x
        val centerX = pointLeftLineX + width / 2f

        // C نقطه میانی نیم دایره بالا
        path.cubicTo(
            pointLeftLineX,
            cubicYToTop,
            cubicXToLeft,
            baseInfo.y,
            centerX,
            baseInfo.y
        )

        // نقطه سمت راست متصل به خط عمودی محور X
        val pointRightLineX = pointLeftLineX + width

        // کشیدن محور x مربوط به یک نقطه به سمت راست
        val cubicXToRight = centerX + width / 4f

        // D نقطه سمت راست بالا
        path.cubicTo(
            cubicXToRight,
            baseInfo.y,
            pointRightLineX,
            cubicYToTop,
            pointRightLineX,
            pointTopLineY
        )

        // E نقطه سمت راست پایین
        path.lineTo(pointRightLineX, pointBottomLineY)

        // کشیدن محور Y مربوط به یک نقطه به سمت پایین
        val cubicYToBottom = pointBottomLineY + semicircularHalfHeight
        val bottom: Float = bottom(baseInfo)
        // F نقطه میانی منحنی پایین
        path.cubicTo(
            pointRightLineX,
            cubicYToBottom,
            cubicXToRight,
            bottom,
            centerX,
            bottom
        )

        // G نقطه سمت چپ پایین
        path.cubicTo(
            cubicXToLeft,
            bottom,
            pointLeftLineX,
            cubicYToBottom,
            pointLeftLineX,
            pointBottomLineY
        )
        path.close()
    }
}