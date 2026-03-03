#!/usr/bin/env python3
"""Generate Android launcher icons from iOS AppIcon.png."""
import os

try:
    from PIL import Image
except ImportError:
    print("Run: pip install Pillow")
    raise

# Paths
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = os.path.dirname(SCRIPT_DIR)
SOURCE = os.path.join(
    PROJECT_ROOT, "..", "opencode_ios_client",
    "OpenCodeClient", "OpenCodeClient", "Assets.xcassets", "AppIcon.appiconset", "AppIcon.png"
)
RES_DIR = os.path.join(PROJECT_ROOT, "app", "src", "main", "res")

# Android mipmap sizes for launcher icon (ic_launcher.png)
MIPMAP_SIZES = {
    "mipmap-mdpi": 48,
    "mipmap-hdpi": 72,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192,
}

# Adaptive icon foreground sizes (108dp at each density)
FOREGROUND_SIZES = {
    "drawable-mdpi": 108,
    "drawable-hdpi": 162,
    "drawable-xhdpi": 216,
    "drawable-xxhdpi": 324,
    "drawable-xxxhdpi": 432,
}


def resize_icons():
    if not os.path.exists(SOURCE):
        print(f"Source not found: {SOURCE}")
        return False

    with Image.open(SOURCE) as img:
        if img.mode != "RGBA":
            img = img.convert("RGBA")

        # Mipmap launcher icons
        for folder, size in MIPMAP_SIZES.items():
            out_dir = os.path.join(RES_DIR, folder)
            os.makedirs(out_dir, exist_ok=True)
            out_path = os.path.join(out_dir, "ic_launcher.png")
            resized = img.resize((size, size), Image.Resampling.LANCZOS)
            resized.save(out_path, "PNG")
            print(f"Saved {out_path} ({size}x{size})")

            # Round icon (same as regular for now)
            round_path = os.path.join(out_dir, "ic_launcher_round.png")
            resized.save(round_path, "PNG")
            print(f"Saved {round_path}")

        # Adaptive icon foreground
        for folder, size in FOREGROUND_SIZES.items():
            out_dir = os.path.join(RES_DIR, folder)
            os.makedirs(out_dir, exist_ok=True)
            out_path = os.path.join(out_dir, "ic_launcher_foreground.png")
            resized = img.resize((size, size), Image.Resampling.LANCZOS)
            resized.save(out_path, "PNG")
            print(f"Saved {out_path} ({size}x{size})")

    return True


if __name__ == "__main__":
    resize_icons()
