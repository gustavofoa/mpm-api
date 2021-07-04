from PIL import Image, ImageOps, ImageDraw
import glob, os, math

for infile in glob.glob("diasLiturgicos/*.*"):
    name = os.path.basename(infile)
    file, ext = os.path.splitext(infile)
    im = Image.open(infile)

    height = math.floor(im.size[1] * 300 / im.size[0])
    output = ImageOps.fit(im, (300, height), centering=(0.5, 0.5))
    output.save("diasLiturgicos/300/" + name)

    output = ImageOps.fit(im, (80, 80), centering=(0.5, 0.5))
    output.save("diasLiturgicos/80x80/" + name)
